package com.mybudget.app.domain.model

/** A launchable application discovered on this device. */
data class InstalledApplication(
    val packageName: String,
    val displayName: String
)
