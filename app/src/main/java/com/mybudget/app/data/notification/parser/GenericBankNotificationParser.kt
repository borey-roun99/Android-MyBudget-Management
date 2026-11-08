package com.mybudget.app.data.notification.parser

import com.mybudget.app.domain.model.NotificationSnapshot
import com.mybudget.app.domain.model.ParsedBankTransaction
import com.mybudget.app.domain.notification.BankTransactionParser
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

/**
 * A package-agnostic starter parser for common English payment notification
 * formats. It is run only for apps selected by the user in MyBudget.
 *
 * Different banks may use different wording or layouts. Add a dedicated parser
 * when a bank's real notification format cannot be recognized reliably.
 */
class GenericBankNotificationParser @Inject constructor() : BankTransactionParser {

    override fun parse(snapshot: NotificationSnapshot): ParsedBankTransaction? {
        val message = "${snapshot.title} ${snapshot.content}".trim()
        if (message.isBlank()) return null

        val normalized = message.lowercase(Locale.ROOT)

        // Avoid recording failed, reversed, incoming, or credited transactions.
        val failedIndicators = listOf(
            "failed", "declined", "unsuccessful", "cancelled", "canceled", "reversed"
        )
        if (failedIndicators.any(normalized::contains)) return null

        val incomingIndicators = listOf(
            "received", "credited", "deposit", "cash in", "refund", "incoming"
        )
        if (incomingIndicators.any(normalized::contains)) return null

        val outgoingIndicators = listOf(
            "payment", "paid", "purchase", "debit", "transfer", "withdraw",
            "spent", "sent", "transaction"
        )
        if (outgoingIndicators.none(normalized::contains)) return null

        val match = MONEY_REGEX.find(message) ?: return null
        val currencyToken = (match.groups[1]?.value ?: match.groups[4]?.value)
            ?.uppercase(Locale.ROOT) ?: return null
        val amountText = match.groups[2]?.value ?: match.groups[3]?.value ?: return null

        val currencyCode = when (currencyToken) {
            "$", "US$", "USD" -> "USD"
            "៛", "KHR" -> "KHR"
            else -> return null
        }

        val fractionDigits = Currency.getInstance(currencyCode).defaultFractionDigits.coerceAtLeast(0)
        val amountMinor = runCatching {
            BigDecimal(amountText.replace(",", ""))
                .movePointRight(fractionDigits)
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact()
        }.getOrNull()?.takeIf { it > 0L } ?: return null

        return ParsedBankTransaction(
            amountMinor = amountMinor,
            currencyCode = currencyCode,
            description = snapshot.appName
        )
    }

    private companion object {
        // Supports: USD 3.00, $3.00, 3.00 USD, and ៛ 12000.
        val MONEY_REGEX = Regex(
            """(?i)(USD|KHR|US\$|\$|៛)\s*([0-9][0-9,]*(?:\.[0-9]{1,2})?)|([0-9][0-9,]*(?:\.[0-9]{1,2})?)\s*(USD|KHR|៛)"""
        )
    }
}
