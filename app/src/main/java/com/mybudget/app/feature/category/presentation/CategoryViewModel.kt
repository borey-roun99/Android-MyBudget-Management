package com.mybudget.app.feature.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybudget.app.domain.model.Category
import com.mybudget.app.domain.usecase.category.AddCategoryUseCase
import com.mybudget.app.domain.usecase.category.DeleteCategoryUseCase
import com.mybudget.app.domain.usecase.category.GetCategorySpendingsUseCase
import com.mybudget.app.domain.usecase.category.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getCategorySpendingsUseCase: GetCategorySpendingsUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    val categorySpendings = getCategorySpendingsUseCase()

    fun addCategory(name: String, iconEmoji: String) {
        viewModelScope.launch {
            runCatching { addCategoryUseCase(name, iconEmoji) }
        }
    }

    fun updateCategory(category: Category, name: String, iconEmoji: String) {
        viewModelScope.launch {
            runCatching { updateCategoryUseCase(category, name, iconEmoji) }
        }
    }

    fun deleteCategory(categoryId: Long) {
        viewModelScope.launch {
            runCatching { deleteCategoryUseCase(categoryId) }
        }
    }
}
