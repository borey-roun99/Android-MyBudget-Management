package com.mybudget.app.di

import com.mybudget.app.data.repository.CategoryRepositoryImpl
import com.mybudget.app.data.repository.TransactionRepositoryImpl
import com.mybudget.app.domain.repository.CategoryRepository
import com.mybudget.app.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(implementation: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(implementation: TransactionRepositoryImpl): TransactionRepository
}
