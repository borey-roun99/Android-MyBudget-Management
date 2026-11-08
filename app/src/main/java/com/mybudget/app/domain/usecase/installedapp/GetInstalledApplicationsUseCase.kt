package com.mybudget.app.domain.usecase.installedapp

import com.mybudget.app.domain.model.InstalledApplication
import com.mybudget.app.domain.repository.InstalledAppRepository
import javax.inject.Inject

class GetInstalledApplicationsUseCase @Inject constructor(
    private val repository: InstalledAppRepository
) {
    suspend operator fun invoke(): List<InstalledApplication> =
        repository.getLaunchableApplications()
}
