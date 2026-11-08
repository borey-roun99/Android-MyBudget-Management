package com.mybudget.app.domain.usecase.category

import com.mybudget.app.domain.model.CategorySpending
import com.mybudget.app.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategorySpendingsUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<CategorySpending>> = repository.observeCategorySpendings()
}
