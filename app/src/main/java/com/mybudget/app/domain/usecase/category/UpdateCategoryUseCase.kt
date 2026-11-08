package com.mybudget.app.domain.usecase.category

import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.repository.CategoryRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category, name: String, iconEmoji: String) {
        val normalizedName = name.trim()
        require(normalizedName.isNotBlank()) { "Category name cannot be empty." }
        repository.updateCategory(category.copy(name = normalizedName, iconEmoji = iconEmoji))
    }
}
