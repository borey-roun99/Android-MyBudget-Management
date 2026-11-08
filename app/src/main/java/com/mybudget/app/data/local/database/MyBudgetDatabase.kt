package com.mybudget.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mybudget.app.data.local.dao.CategoryDao
import com.mybudget.app.data.local.dao.TransactionDao
import com.mybudget.app.data.local.entity.CategoryEntity
import com.mybudget.app.data.local.entity.TransactionEntity

@Database(
    entities = [CategoryEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MyBudgetDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}
