package com.mybudget.app.data.notification.parser

import com.mybudget.app.domain.model.NotificationSnapshot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GenericBankNotificationParserTest {
    private val parser = GenericBankNotificationParser()

    @Test
    fun `parses outgoing USD notification from an arbitrary selected app`() {
        val result = parser.parse(
            NotificationSnapshot(
                packageName = "com.example.paymentapp",
                title = "Payment successful",
                content = "You paid USD 3.00",
                postedAt = 1_700_000_000_000,
                notificationKey = "test-1"
            )
        )

        assertEquals(300L, result?.amountMinor)
        assertEquals("USD", result?.currencyCode)
    }

    @Test
    fun `parses dollar symbol notification from an arbitrary selected app`() {
        val result = parser.parse(
            NotificationSnapshot(
                packageName = "org.example.mobilebank",
                title = "Purchase paid",
                content = "Amount: \$3.50",
                postedAt = 1_700_000_000_000,
                notificationKey = "test-2"
            )
        )

        assertEquals(350L, result?.amountMinor)
    }

    @Test
    fun `ignores incoming money notification`() {
        val result = parser.parse(
            NotificationSnapshot(
                packageName = "com.example.paymentapp",
                title = "Money received",
                content = "You received USD 50.00",
                postedAt = 1_700_000_000_000,
                notificationKey = "test-3"
            )
        )

        assertNull(result)
    }

    @Test
    fun `ignores failed payment notification`() {
        val result = parser.parse(
            NotificationSnapshot(
                packageName = "com.example.paymentapp",
                title = "Payment failed",
                content = "Payment of USD 3.00 was declined",
                postedAt = 1_700_000_000_000,
                notificationKey = "test-4"
            )
        )

        assertNull(result)
    }
}
