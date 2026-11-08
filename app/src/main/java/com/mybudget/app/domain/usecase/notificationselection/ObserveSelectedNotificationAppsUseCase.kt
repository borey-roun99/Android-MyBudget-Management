package com.mybudget.app.domain.usecase.notificationselection

import com.mybudget.app.domain.repository.NotificationAppSelectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSelectedNotificationAppsUseCase @Inject constructor(
    private val repository: NotificationAppSelectionRepository
) {
    operator fun invoke(): Flow<Set<String>> = repository.observeSelectedPackageNames()
}
