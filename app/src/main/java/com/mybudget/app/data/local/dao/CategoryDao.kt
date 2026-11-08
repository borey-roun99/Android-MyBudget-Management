package com.mybudget.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mybudget.app.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name COLLATE NOCASE ASC")
    fun observeCategories(): Flow<List<CategoryEntity>>

    @Query(
        """
        SELECT
            c.id AS categoryId,
            c.name AS categoryName,
            c.iconEmoji AS iconEmoji,
            c.colorHex AS colorHex,
            COALESCE(t.currencyCode, 'USD') AS currencyCode,
            COALESCE(SUM(t.amountMinor), 0) AS totalAmountMinor,
            COUNT(t.id) AS transactionCount
        FROM categories c
        LEFT JOIN transactions t ON t.categoryId = c.id
        GROUP BY c.id, t.currencyCode
        ORDER BY c.name COLLATE NOCASE ASC
        """
    )
    fun observeCategorySpendings(): Flow<List<CategorySpendingProjection>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(category: CategoryEntity): Long

    @Update
    suspend fun update(category: CategoryEntity)

    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun deleteById(categoryId: Long)
}
