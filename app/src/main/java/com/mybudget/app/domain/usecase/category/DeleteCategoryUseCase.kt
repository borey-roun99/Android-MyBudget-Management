package com.mybudget.app.domain.usecase.category

import com.mybudget.app.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Long) = repository.deleteCategory(categoryId)
}
