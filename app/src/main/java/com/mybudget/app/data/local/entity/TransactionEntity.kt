package com.mybudget.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["notificationKey"], unique = true),
        Index(value = ["categoryId"]),
        Index(value = ["transactionDate"])
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amountMinor: Long,
    val currencyCode: String,
    val description: String,
    val bankPackage: String,
    val categoryId: Long? = null,
    val notificationKey: String,
    val transactionDate: Long
)
