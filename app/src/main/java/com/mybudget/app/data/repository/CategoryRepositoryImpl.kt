package com.mybudget.app.data.repository

import com.mybudget.app.data.local.dao.CategoryDao
import com.mybudget.app.data.mapper.toDomain
import com.mybudget.app.data.mapper.toEntity
import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.model.CategorySpending
import com.mybudget.app.domain.model.MoneyAmount
import com.mybudget.app.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.observeCategories().map { entities -> entities.map { it.toDomain() } }

    override fun observeCategorySpendings(): Flow<List<CategorySpending>> =
        categoryDao.observeCategorySpendings().map { rows ->
            rows.groupBy { it.categoryId }.values.map { categoryRows ->
                val first = categoryRows.first()
                CategorySpending(
                    category = Category(
                        id = first.categoryId,
                        name = first.categoryName,
                        iconEmoji = first.iconEmoji,
                        colorHex = first.colorHex
                    ),
                    totals = categoryRows
                        .filter { it.totalAmountMinor > 0L }
                        .map {
                            MoneyAmount(
                                amountMinor = it.totalAmountMinor,
                                currencyCode = it.currencyCode
                            )
                        },
                    transactionCount = categoryRows.sumOf { it.transactionCount }
                )
            }
        }

    override suspend fun addCategory(category: Category): Long =
        categoryDao.insert(category.toEntity())

    override suspend fun updateCategory(category: Category) {
        categoryDao.update(category.toEntity())
    }

    override suspend fun deleteCategory(categoryId: Long) {
        categoryDao.deleteById(categoryId)
    }
}
