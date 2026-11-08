package com.mybudget.app.domain.usecase.transaction

import com.mybudget.app.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateTransactionCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Long, categoryId: Long?) {
        repository.updateTransactionCategory(transactionId, categoryId)
    }
}
