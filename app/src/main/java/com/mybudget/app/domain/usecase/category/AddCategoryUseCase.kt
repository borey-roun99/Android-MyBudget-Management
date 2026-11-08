package com.mybudget.app.domain.usecase.category

import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.repository.CategoryRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(name: String, iconEmoji: String) {
        val normalizedName = name.trim()
        require(normalizedName.isNotBlank()) { "Category name cannot be empty." }
        repository.addCategory(
            Category(
                id = 0,
                name = normalizedName,
                iconEmoji = iconEmoji,
                colorHex = "#64748B"
            )
        )
    }
}
