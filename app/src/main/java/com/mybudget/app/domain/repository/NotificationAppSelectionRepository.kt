package com.mybudget.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface NotificationAppSelectionRepository {
    fun observeSelectedPackageNames(): Flow<Set<String>>
    suspend fun updateSelectedPackageNames(packageNames: Set<String>)
}
