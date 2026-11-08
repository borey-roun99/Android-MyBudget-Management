package com.mybudget.app.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.mybudget.app.R

/**
 * Inter is requested from the Google Fonts provider at runtime.
 * A platform sans-serif fallback is used by Compose if the provider cannot
 * supply the font (for example, on a device without Google Play services).
 */
private val googleFontsProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val inter = GoogleFont("Inter")

private val MyBudgetFontFamily = FontFamily(
    Font(
        googleFont = inter,
        fontProvider = googleFontsProvider,
        weight = FontWeight.Normal
    ),
    Font(
        googleFont = inter,
        fontProvider = googleFontsProvider,
        weight = FontWeight.Medium
    ),
    Font(
        googleFont = inter,
        fontProvider = googleFontsProvider,
        weight = FontWeight.SemiBold
    ),
    Font(
        googleFont = inter,
        fontProvider = googleFontsProvider,
        weight = FontWeight.Bold
    )
)

val MyBudgetTypography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = MyBudgetFontFamily),
        displayMedium = displayMedium.copy(fontFamily = MyBudgetFontFamily),
        displaySmall = displaySmall.copy(fontFamily = MyBudgetFontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = MyBudgetFontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = MyBudgetFontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = MyBudgetFontFamily),
        titleLarge = titleLarge.copy(fontFamily = MyBudgetFontFamily),
        titleMedium = titleMedium.copy(fontFamily = MyBudgetFontFamily),
        titleSmall = titleSmall.copy(fontFamily = MyBudgetFontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = MyBudgetFontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = MyBudgetFontFamily),
        bodySmall = bodySmall.copy(fontFamily = MyBudgetFontFamily),
        labelLarge = labelLarge.copy(fontFamily = MyBudgetFontFamily),
        labelMedium = labelMedium.copy(fontFamily = MyBudgetFontFamily),
        labelSmall = labelSmall.copy(fontFamily = MyBudgetFontFamily)
    )
}
