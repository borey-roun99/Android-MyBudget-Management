package com.mybudget.app.feature.settings.presentation

import com.mybudget.app.domain.model.InstalledApplication

data class BankAppSelectionUiState(
    val isLoadingApps: Boolean = true,
    val installedApps: List<InstalledApplication> = emptyList(),
    val selectedPackageNames: Set<String> = emptySet(),
    val searchQuery: String = "",
    val errorMessage: String? = null
) {
    val filteredApps: List<InstalledApplication>
        get() = if (searchQuery.isBlank()) {
            installedApps
        } else {
            installedApps.filter { app ->
                app.displayName.contains(searchQuery, ignoreCase = true) ||
                    app.packageName.contains(searchQuery, ignoreCase = true)
            }
        }
}
