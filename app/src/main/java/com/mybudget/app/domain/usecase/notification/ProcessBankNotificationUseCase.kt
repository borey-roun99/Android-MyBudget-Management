package com.mybudget.app.domain.usecase.notification

import com.mybudget.app.domain.model.NotificationSnapshot
import com.mybudget.app.domain.model.Transaction
import com.mybudget.app.domain.notification.BankTransactionParser
import com.mybudget.app.domain.repository.TransactionRepository
import javax.inject.Inject

class ProcessBankNotificationUseCase @Inject constructor(
    parsers: Set<@JvmSuppressWildcards BankTransactionParser>,
    private val transactionRepository: TransactionRepository
) {
    private val transactionParsers = parsers.toList()

    suspend operator fun invoke(snapshot: NotificationSnapshot) {
        // The NotificationListenerService already filters by the user's selected
        // app packages. No bank package IDs need to be hardcoded here.
        val parsed = transactionParsers.firstNotNullOfOrNull { parser ->
            parser.parse(snapshot)
        } ?: return

        transactionRepository.addTransaction(
            Transaction(
                id = 0,
                amountMinor = parsed.amountMinor,
                currencyCode = parsed.currencyCode,
                description = parsed.description,
                bankPackage = snapshot.packageName,
                categoryId = null,
                notificationKey = snapshot.notificationKey,
                transactionDate = snapshot.postedAt
            )
        )
    }
}
