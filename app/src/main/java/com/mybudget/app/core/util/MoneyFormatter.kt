package com.mybudget.app.core.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatMinorUnits(amountMinor: Long, currencyCode: String): String {
    return runCatching {
        val currency = Currency.getInstance(currencyCode)
        val fractionDigits = currency.defaultFractionDigits.coerceAtLeast(0)
        val amount = BigDecimal.valueOf(amountMinor, fractionDigits)
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            this.currency = currency
            minimumFractionDigits = fractionDigits
            maximumFractionDigits = fractionDigits
        }.format(amount)
    }.getOrElse {
        "$currencyCode ${amountMinor}"
    }
}
