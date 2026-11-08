package com.mybudget.app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val NOTIFICATION_APP_PREFERENCES = "notification_app_preferences"

val Context.notificationAppDataStore: DataStore<Preferences> by preferencesDataStore(
    name = NOTIFICATION_APP_PREFERENCES
)
