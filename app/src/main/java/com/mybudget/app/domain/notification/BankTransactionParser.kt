package com.mybudget.app.domain.notification

import com.mybudget.app.domain.model.NotificationSnapshot
import com.mybudget.app.domain.model.ParsedBankTransaction

/**
 * Parses a payment/expense transaction from a notification belonging to an app
 * explicitly selected by the user in MyBudget.
 *
 * Implementations must be conservative: return null when the notification does
 * not clearly describe an outgoing transaction.
 */
interface BankTransactionParser {
    fun parse(snapshot: NotificationSnapshot): ParsedBankTransaction?
}
