package com.mybudget.app.feature.transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybudget.app.domain.usecase.category.GetCategoriesUseCase
import com.mybudget.app.domain.usecase.transaction.GetTransactionsUseCase
import com.mybudget.app.domain.usecase.transaction.UpdateTransactionCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val updateTransactionCategoryUseCase: UpdateTransactionCategoryUseCase
) : ViewModel() {

    val uiState = combine(
        getTransactionsUseCase(),
        getCategoriesUseCase()
    ) { transactions, categories ->
        TransactionUiState(transactions, categories)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TransactionUiState()
    )

    fun assignCategory(transactionId: Long, categoryId: Long?) {
        viewModelScope.launch {
            runCatching { updateTransactionCategoryUseCase(transactionId, categoryId) }
        }
    }
}
