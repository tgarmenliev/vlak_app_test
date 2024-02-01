package com.example.vlak_app_test.ui.guide

import androidx.lifecycle.ViewModel
import com.example.vlak_app_test.models.guide.Guide

sealed interface GuideState {
    object Loading: GuideState
    data class Success(val data: Guide.Topic) : GuideState
    data class Error(val error: Throwable) : GuideState
}

class GuideViewModel : ViewModel() {

}