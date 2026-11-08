package com.mybudget.app.domain.repository

import com.mybudget.app.domain.model.InstalledApplication

interface InstalledAppRepository {
    suspend fun getLaunchableApplications(): List<InstalledApplication>
}
