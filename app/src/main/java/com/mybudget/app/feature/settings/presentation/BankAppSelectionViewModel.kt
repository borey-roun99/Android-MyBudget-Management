package com.mybudget.app.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybudget.app.domain.usecase.installedapp.GetInstalledApplicationsUseCase
import com.mybudget.app.domain.usecase.notificationselection.ObserveSelectedNotificationAppsUseCase
import com.mybudget.app.domain.usecase.notificationselection.UpdateSelectedNotificationAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class BankAppSelectionViewModel @Inject constructor(
    private val getInstalledApplications: GetInstalledApplicationsUseCase,
    private val observeSelectedNotificationApps: ObserveSelectedNotificationAppsUseCase,
    private val updateSelectedNotificationApps: UpdateSelectedNotificationAppsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BankAppSelectionUiState())
    val uiState: StateFlow<BankAppSelectionUiState> = _uiState.asStateFlow()

    private val selectionMutex = Mutex()
    private var loadAppsJob: Job? = null

    init {
        observeSelection()
        refreshApps()
    }

    private fun observeSelection() {
        viewModelScope.launch {
            observeSelectedNotificationApps()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(errorMessage = throwable.message ?: "Unable to read saved app selections.")
                    }
                }
                .collect { packageNames ->
                    _uiState.update {
                        it.copy(selectedPackageNames = packageNames)
                    }
                }
        }
    }

    fun refreshApps() {
        loadAppsJob?.cancel()
        loadAppsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoadingApps = true, errorMessage = null) }
            try {
                val apps = getInstalledApplications()
                _uiState.update {
                    it.copy(isLoadingApps = false, installedApps = apps)
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (throwable: Throwable) {
                _uiState.update {
                    it.copy(
                        isLoadingApps = false,
                        errorMessage = throwable.message ?: "Unable to load installed applications."
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onAppSelectionChanged(packageName: String, isSelected: Boolean) {
        viewModelScope.launch {
            selectionMutex.withLock {
                val previous = _uiState.value.selectedPackageNames
                val updated = previous.toMutableSet().apply {
                    if (isSelected) add(packageName) else remove(packageName)
                }.toSet()

                _uiState.update { it.copy(selectedPackageNames = updated, errorMessage = null) }

                try {
                    updateSelectedNotificationApps(updated)
                } catch (cancellation: CancellationException) {
                    throw cancellation
                } catch (throwable: Throwable) {
                    _uiState.update {
                        it.copy(
                            selectedPackageNames = previous,
                            errorMessage = throwable.message ?: "Unable to save app selection."
                        )
                    }
                }
            }
        }
    }
}
