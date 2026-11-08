package com.mybudget.app.feature.transaction.presentation

import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.model.Transaction

data class TransactionUiState(
    val transactions: List<Transaction> = emptyList(),
    val categories: List<Category> = emptyList()
)
