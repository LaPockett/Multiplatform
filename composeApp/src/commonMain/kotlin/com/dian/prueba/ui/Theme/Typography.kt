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
import org.jetbrains.compose.resources.Font

@Suppress("ComposableNaming")
@Composable
fun Typography(): Typography {
    val bellota = FontFamily(
        Font(Res.font.BellotaText_Bold, FontWeight.Bold),
        Font(Res.font.BellotaText_BoldItalic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.BellotaText_Light, FontWeight.Light),
        Font(Res.font.BellotaText_Regular, FontWeight.Normal),
        Font(Res.font.BellotaText_Italic, FontWeight.Medium, FontStyle.Italic),
        Font(Res.font.BellotaText_LightItalic, FontWeight.Light, FontStyle.Italic),
    )
    return Typography(
        headlineLarge = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        titleLarge = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        titleMedium = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        titleSmall = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        bodySmall = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        displayLarge = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        displayMedium = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        displaySmall = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        labelSmall = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        labelMedium = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        labelLarge = TextStyle(
            fontFamily = bellota,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
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