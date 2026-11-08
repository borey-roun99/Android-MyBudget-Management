package com.mybudget.app.domain.model

data class ParsedBankTransaction(
    val amountMinor: Long,
    val currencyCode: String,
    val description: String
)
