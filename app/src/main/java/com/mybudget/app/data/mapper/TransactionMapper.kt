package com.mybudget.app.data.mapper

import com.mybudget.app.data.local.entity.TransactionEntity
import com.mybudget.app.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    amountMinor = amountMinor,
    currencyCode = currencyCode,
    description = description,
    bankPackage = bankPackage,
    categoryId = categoryId,
    notificationKey = notificationKey,
    transactionDate = transactionDate
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    amountMinor = amountMinor,
    currencyCode = currencyCode,
    description = description,
    bankPackage = bankPackage,
    categoryId = categoryId,
    notificationKey = notificationKey,
    transactionDate = transactionDate
)
