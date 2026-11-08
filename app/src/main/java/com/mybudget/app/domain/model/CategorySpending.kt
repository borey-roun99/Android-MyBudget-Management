package com.mybudget.app.domain.model

data class CategorySpending(
    val category: Category,
    val totals: List<MoneyAmount>,
    val transactionCount: Int
)
