package com.dian.prueba.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.airbnb.android.showkase.annotation.ShowkaseTypography

@ShowkaseTypography(group = "Typography", name = "Body Large Style")
val bodyLarge = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)
@ShowkaseTypography(group = "Typography", name = "Body Medium Style")
val bodyMedium  = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.15.sp
)
@ShowkaseTypography(group = "Typography", name = "Body Small Style")
val bodySmall = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.30.sp
)