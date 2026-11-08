package com.mybudget.app.feature.dashboard.presentation

import com.mybudget.app.domain.model.MoneyAmount
import com.mybudget.app.domain.model.Transaction

data class DashboardUiState(
    val todayTotals: List<MoneyAmount> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList()
)
