package com.mybudget.app.domain.usecase.transaction

import com.mybudget.app.domain.model.MoneyAmount
import com.mybudget.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetTodaySpendingUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<MoneyAmount>> {
        // Calendar keeps this compatible with the app's minSdk without requiring core-library desugaring.
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startMillis = startOfDay.timeInMillis
        val endMillis = (startOfDay.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }.timeInMillis

        return repository.observeSpendingBetween(startMillis, endMillis)
    }
}
