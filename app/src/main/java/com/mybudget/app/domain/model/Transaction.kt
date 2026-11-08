package com.mybudget.app.domain.model

data class Transaction(
    val id: Long,
    val amountMinor: Long,
    val currencyCode: String,
    val description: String,
    val bankPackage: String,
    val categoryId: Long?,
    val notificationKey: String,
    val transactionDate: Long
)
