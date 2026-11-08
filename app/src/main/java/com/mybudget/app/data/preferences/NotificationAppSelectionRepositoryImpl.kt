package com.mybudget.app.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.mybudget.app.domain.repository.NotificationAppSelectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationAppSelectionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : NotificationAppSelectionRepository {

    override fun observeSelectedPackageNames(): Flow<Set<String>> =
        dataStore.data
            .map { preferences -> preferences[SELECTED_PACKAGE_NAMES].orEmpty() }
            .catch { emit(emptySet()) }

    override suspend fun updateSelectedPackageNames(packageNames: Set<String>) {
        dataStore.edit { preferences ->
            preferences[SELECTED_PACKAGE_NAMES] = packageNames
        }
    }

    private companion object {
        val SELECTED_PACKAGE_NAMES = stringSetPreferencesKey("selected_notification_app_packages")
    }
}
