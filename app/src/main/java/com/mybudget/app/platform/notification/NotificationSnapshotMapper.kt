package com.mybudget.app.platform.notification

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.mybudget.app.domain.model.NotificationSnapshot
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import javax.inject.Inject
class NotificationSnapshotMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun map(
        sbn: StatusBarNotification
    ): NotificationSnapshot {

        val packageName = sbn.packageName
        val extras = sbn.notification.extras

        val appName = getAppDisplayName(packageName)

        return NotificationSnapshot(
            packageName = packageName,
            appName = appName,
            title = extras.getCharSequence(
                Notification.EXTRA_TITLE
            )?.toString(),
            content = extras.getCharSequence(
                Notification.EXTRA_TEXT
            )?.toString(),
            postedAt = sbn.postTime,
            notificationKey = sbn.key
        )
    }

    private fun getAppDisplayName(packageName: String): String {
        return try {
            val packageManager = context.packageManager

            val applicationInfo = if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ) {
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.ApplicationInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getApplicationInfo(packageName, 0)
            }

            packageManager.getApplicationLabel(applicationInfo).toString()

        } catch (e: PackageManager.NameNotFoundException) {
            packageName
        }
    }
}
