package com.mybudget.app.di

import com.mybudget.app.data.notification.parser.GenericBankNotificationParser
import com.mybudget.app.domain.notification.BankTransactionParser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoSet
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationParserModule {
    @Binds
    @IntoSet
    abstract fun bindGenericBankNotificationParser(
        parser: GenericBankNotificationParser
    ): BankTransactionParser
}
