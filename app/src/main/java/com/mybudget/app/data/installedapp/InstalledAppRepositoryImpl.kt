package com.mybudget.app.data.installedapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import com.mybudget.app.domain.model.InstalledApplication
import com.mybudget.app.domain.repository.InstalledAppRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InstalledAppRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : InstalledAppRepository {

    override suspend fun getLaunchableApplications(): List<InstalledApplication> =
        withContext(Dispatchers.IO) {
            val packageManager = context.packageManager
            val launcherIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val resolvedActivities = queryLauncherActivities(packageManager, launcherIntent)

            resolvedActivities
                .mapNotNull { resolveInfo -> resolveInfo.activityInfo?.applicationInfo }
                .asSequence()
                .filterNot { applicationInfo -> applicationInfo.packageName == context.packageName }
                .distinctBy { applicationInfo -> applicationInfo.packageName }
                .mapNotNull { applicationInfo ->
                    runCatching {
                        InstalledApplication(
                            packageName = applicationInfo.packageName,
                            displayName = packageManager.getApplicationLabel(applicationInfo)
                                .toString()
                                .trim()
                        )
                    }.getOrNull()
                }
                .filter { it.displayName.isNotBlank() }
                .sortedBy { it.displayName.lowercase() }
                .toList()
        }

    @Suppress("DEPRECATION")
    private fun queryLauncherActivities(
        packageManager: PackageManager,
        intent: Intent
    ): List<ResolveInfo> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(
            intent,
            PackageManager.ResolveInfoFlags.of(0L)
        )
    } else {
        packageManager.queryIntentActivities(intent, 0)
    }
}
