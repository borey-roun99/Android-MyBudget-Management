package com.mybudget.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mybudget.app.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY transactionDate DESC")
    fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY transactionDate DESC LIMIT :limit")
    fun observeRecentTransactions(limit: Int): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT
            currencyCode AS currencyCode,
            COALESCE(SUM(amountMinor), 0) AS totalAmountMinor
        FROM transactions
        WHERE transactionDate >= :startMillis AND transactionDate < :endMillis
        GROUP BY currencyCode
        ORDER BY currencyCode ASC
        """
    )
    fun observeSpendingBetween(
        startMillis: Long,
        endMillis: Long
    ): Flow<List<MoneyTotalProjection>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("UPDATE transactions SET categoryId = :categoryId WHERE id = :transactionId")
    suspend fun updateCategory(transactionId: Long, categoryId: Long?)
}
