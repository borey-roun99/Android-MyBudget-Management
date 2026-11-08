package com.mybudget.app.domain.repository

import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.model.CategorySpending
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategories(): Flow<List<Category>>
    fun observeCategorySpendings(): Flow<List<CategorySpending>>
    suspend fun addCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(categoryId: Long)
}
