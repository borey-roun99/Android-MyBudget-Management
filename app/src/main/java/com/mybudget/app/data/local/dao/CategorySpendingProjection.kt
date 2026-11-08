package com.mybudget.app.data.local.dao

data class CategorySpendingProjection(
    val categoryId: Long,
    val categoryName: String,
    val iconEmoji: String,
    val colorHex: String,
    val currencyCode: String,
    val totalAmountMinor: Long,
    val transactionCount: Int
)
