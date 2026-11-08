package com.mybudget.app.domain.model

data class NotificationSnapshot(
    val packageName: String,
    val appName: String,
    val title: String?,
    val content: String?,
    val postedAt: Long,
    val notificationKey: String
)
