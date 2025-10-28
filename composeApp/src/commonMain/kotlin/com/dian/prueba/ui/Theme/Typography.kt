package com.dian.prueba.ui.Theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import multiplatform.composeapp.generated.resources.BellotaText_Bold
import multiplatform.composeapp.generated.resources.BellotaText_BoldItalic
import multiplatform.composeapp.generated.resources.BellotaText_Italic
import multiplatform.composeapp.generated.resources.BellotaText_Light
import multiplatform.composeapp.generated.resources.BellotaText_LightItalic
import multiplatform.composeapp.generated.resources.BellotaText_Regular
import multiplatform.composeapp.generated.resources.Res
import multiplatform.composeapp.generated.resources.Ubuntu_Bold
import multiplatform.composeapp.generated.resources.Ubuntu_BoldItalic
import multiplatform.composeapp.generated.resources.Ubuntu_Italic
import multiplatform.composeapp.generated.resources.Ubuntu_Light
import multiplatform.composeapp.generated.resources.Ubuntu_LightItalic
import multiplatform.composeapp.generated.resources.Ubuntu_Medium
import multiplatform.composeapp.generated.resources.Ubuntu_MediumItalic
import multiplatform.composeapp.generated.resources.Ubuntu_Regular
import org.jetbrains.compose.resources.Font


@Suppress("ComposableNaming")
@Composable
fun BellotaFamily() = FontFamily(
        Font(Res.font.BellotaText_Bold, FontWeight.Bold),
        Font(Res.font.BellotaText_BoldItalic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.BellotaText_Light, FontWeight.Light),
        Font(Res.font.BellotaText_Regular, FontWeight.Normal),
        Font(Res.font.BellotaText_Italic, FontWeight.Medium, FontStyle.Italic),
        Font(Res.font.BellotaText_LightItalic, FontWeight.Light, FontStyle.Italic))
@Suppress("ComposableNaming")
@Composable
fun BellotaTypography() = Typography().run{
    val fontFamily = BellotaFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily, fontWeight = FontWeight.Bold)
    )
}

@Suppress("ComposableNaming")
@Composable
fun UbuntuFamily() = FontFamily(
    Font(Res.font.Ubuntu_Bold, FontWeight.Bold),
    Font(Res.font.Ubuntu_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.Ubuntu_Italic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.Ubuntu_Light, FontWeight.Light),
    Font(Res.font.Ubuntu_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.Ubuntu_Medium, FontWeight.Medium),
    Font(Res.font.Ubuntu_MediumItalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.Ubuntu_Regular, FontWeight.Normal))

@Suppress("ComposableNaming")
@Composable
fun UbuntuTypography() = Typography().run{
    val fontFamily = UbuntuFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily, fontWeight = FontWeight.Bold)
    )
}
/*
// Basic Usage
val Typography: Typography = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.30.sp
    )
)*/