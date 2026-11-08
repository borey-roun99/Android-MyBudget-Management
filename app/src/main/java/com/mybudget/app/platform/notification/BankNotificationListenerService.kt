package com.mybudget.app.platform.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.mybudget.app.domain.usecase.notification.ProcessBankNotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class BankNotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var notificationSnapshotMapper: NotificationSnapshotMapper

    @Inject
    lateinit var processBankNotificationUseCase: ProcessBankNotificationUseCase

    private val serviceScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        serviceScope.launch {
            runCatching {
                val snapshot = notificationSnapshotMapper.map(sbn)
                processBankNotificationUseCase(snapshot)
            }.onFailure { _ ->
            }
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }
}