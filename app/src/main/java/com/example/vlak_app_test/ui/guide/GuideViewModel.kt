package com.example.vlak_app_test.ui.guide

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vlak_app_test.models.guide.Guide
import com.example.vlak_app_test.network.TrainApi
import com.example.vlak_app_test.room.SearchedStation
import com.example.vlak_app_test.ui.live.LiveState
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface GuideState {
    object Loading: GuideState
    data class SuccessTopic(val data: Guide.Topic) : GuideState
    data class SuccessAllTopics(val data: List<Guide.AllTopics>) : GuideState
    data class Error(val error: Throwable) : GuideState
}

class GuideViewModel : ViewModel() {
    var guideState: GuideState by mutableStateOf(GuideState.Loading)

    private val _selectedTopic = mutableIntStateOf(-1)
    val selectedTopic: State<Int> = _selectedTopic
    
    init {
        viewModelScope.launch {
            guideState = GuideState.Loading

            guideState = try {
                val result = TrainApi.retrofitService.getGuideTopics(Locale.getDefault().language)
                GuideState.SuccessAllTopics(result)
            } catch (e: Exception) {
                GuideState.Error(e)
            }
        }
    }

    fun getGuideTopic() {
        if (selectedTopic.value == -1) return
        viewModelScope.launch {
            guideState = GuideState.Loading

            guideState = try {
                val result = TrainApi.retrofitService.getGuideTopic(Locale.getDefault().language, selectedTopic.value)
                GuideState.SuccessTopic(result)
            } catch (e: Exception) {
                GuideState.Error(e)
            }
        }
    }

    fun getAllTopics(): List<Guide.AllTopics> {
        if (guideState is GuideState.SuccessAllTopics) {
            return (guideState as GuideState.SuccessAllTopics).data
        }
        return emptyList()
    }
}