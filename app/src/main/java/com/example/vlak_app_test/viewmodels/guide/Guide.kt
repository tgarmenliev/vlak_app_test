package com.example.vlak_app_test.viewmodels.guide

import androidx.compose.ui.graphics.painter.Painter

class Guide {
    data class GuideTable(
        val title: String,
        val shortDescription: String,
        val description: String,
        val image: Painter
    )
}