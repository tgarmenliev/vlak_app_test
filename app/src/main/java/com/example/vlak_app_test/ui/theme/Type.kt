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

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamilyJost,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val JostFont = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.jost_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.jost_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.jost_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.jost_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resId = R.font.jost_black,
            weight = FontWeight.Black
        )
    )
)