package com.mybudget.app.di

import android.content.Context
import androidx.room.Room
import com.mybudget.app.data.local.dao.CategoryDao
import com.mybudget.app.data.local.dao.TransactionDao
import com.mybudget.app.data.local.database.DefaultCategoryCallback
import com.mybudget.app.data.local.database.MyBudgetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyBudgetDatabase =
        Room.databaseBuilder(
            context,
            MyBudgetDatabase::class.java,
            "mybudget.db"
        )
            .addCallback(DefaultCategoryCallback())
            // Suitable for the initial prototype. Replace with explicit migrations before release.
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCategoryDao(database: MyBudgetDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideTransactionDao(database: MyBudgetDatabase): TransactionDao = database.transactionDao()
}
