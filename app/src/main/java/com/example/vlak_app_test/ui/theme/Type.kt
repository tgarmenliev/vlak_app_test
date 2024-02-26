package com.example.vlak_app_test.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vlak_app_test.R

val fontFamilyJost = FontFamily(
    Font(R.font.jost_regular, FontWeight.Normal),
    Font(R.font.jost_medium, FontWeight.Medium),
    Font(R.font.jost_semibold, FontWeight.SemiBold),
    Font(R.font.jost_bold, FontWeight.Bold),
    Font(R.font.jost_black, FontWeight.Black),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
)