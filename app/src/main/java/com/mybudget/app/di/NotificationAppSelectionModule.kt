package com.mybudget.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mybudget.app.data.preferences.notificationAppDataStore
import com.mybudget.app.data.installedapp.InstalledAppRepositoryImpl
import com.mybudget.app.data.preferences.NotificationAppSelectionRepositoryImpl
import com.mybudget.app.domain.repository.InstalledAppRepository
import com.mybudget.app.domain.repository.NotificationAppSelectionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationAppSelectionBindingsModule {

    @Binds
    @Singleton
    abstract fun bindInstalledAppRepository(
        implementation: InstalledAppRepositoryImpl
    ): InstalledAppRepository

    @Binds
    @Singleton
    abstract fun bindNotificationAppSelectionRepository(
        implementation: NotificationAppSelectionRepositoryImpl
    ): NotificationAppSelectionRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NotificationAppDataStoreModule {

    @Provides
    @Singleton
    fun provideNotificationAppDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.notificationAppDataStore
}
