package com.mybudget.app.data.repository

import com.mybudget.app.data.local.dao.TransactionDao
import com.mybudget.app.data.mapper.toDomain
import com.mybudget.app.data.mapper.toEntity
import com.mybudget.app.domain.model.MoneyAmount
import com.mybudget.app.domain.model.Transaction
import com.mybudget.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override fun observeTransactions(): Flow<List<Transaction>> =
        transactionDao.observeTransactions().map { entities -> entities.map { it.toDomain() } }

    override fun observeRecentTransactions(limit: Int): Flow<List<Transaction>> =
        transactionDao.observeRecentTransactions(limit).map { entities -> entities.map { it.toDomain() } }

    override fun observeSpendingBetween(
        startMillis: Long,
        endMillis: Long
    ): Flow<List<MoneyAmount>> = transactionDao.observeSpendingBetween(startMillis, endMillis).map { rows ->
        rows.map { MoneyAmount(it.totalAmountMinor, it.currencyCode) }
    }

    override suspend fun addTransaction(transaction: Transaction): Boolean =
        transactionDao.insert(transaction.toEntity()) != -1L

    override suspend fun updateTransactionCategory(transactionId: Long, categoryId: Long?) {
        transactionDao.updateCategory(transactionId, categoryId)
    }
}
