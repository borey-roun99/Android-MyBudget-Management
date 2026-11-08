package com.mybudget.app.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybudget.app.domain.usecase.transaction.GetRecentTransactionsUseCase
import com.mybudget.app.domain.usecase.transaction.GetTodaySpendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getTodaySpendingUseCase: GetTodaySpendingUseCase,
    getRecentTransactionsUseCase: GetRecentTransactionsUseCase
) : ViewModel() {

    val uiState = combine(
        getTodaySpendingUseCase(),
        getRecentTransactionsUseCase(5)
    ) { totals, recent ->
        DashboardUiState(todayTotals = totals, recentTransactions = recent)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState()
    )
}
