package com.mybudget.app.domain.repository

import com.mybudget.app.domain.model.MoneyAmount
import com.mybudget.app.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun observeTransactions(): Flow<List<Transaction>>
    fun observeRecentTransactions(limit: Int): Flow<List<Transaction>>
    fun observeSpendingBetween(startMillis: Long, endMillis: Long): Flow<List<MoneyAmount>>
    suspend fun addTransaction(transaction: Transaction): Boolean
    suspend fun updateTransactionCategory(transactionId: Long, categoryId: Long?)
}
