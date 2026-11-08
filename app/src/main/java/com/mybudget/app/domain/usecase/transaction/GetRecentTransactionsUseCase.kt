package com.mybudget.app.domain.usecase.transaction

import com.mybudget.app.domain.model.Transaction
import com.mybudget.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(limit: Int = 5): Flow<List<Transaction>> =
        repository.observeRecentTransactions(limit.coerceAtLeast(1))
}
