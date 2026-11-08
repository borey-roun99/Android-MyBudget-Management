package com.mybudget.app.domain.usecase.notificationselection

import com.mybudget.app.domain.repository.NotificationAppSelectionRepository
import javax.inject.Inject

class UpdateSelectedNotificationAppsUseCase @Inject constructor(
    private val repository: NotificationAppSelectionRepository
) {
    suspend operator fun invoke(packageNames: Set<String>) {
        repository.updateSelectedPackageNames(packageNames)
    }
}
