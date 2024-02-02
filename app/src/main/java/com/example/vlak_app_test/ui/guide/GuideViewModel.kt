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

    private val _allTopics = mutableStateOf<List<Guide.AllTopics>>(emptyList())
    
    init {
        viewModelScope.launch {
            guideState = GuideState.Loading

            guideState = try {
                val result = TrainApi.retrofitService.getGuideTopics(Locale.getDefault().language)
                _allTopics.value = result
                GuideState.SuccessAllTopics(result)
            } catch (e: Exception) {
                GuideState.Error(e)
            }
        }
    }

    fun setSelectedTopic(id: Int) {
        _selectedTopic.value = id
    }

    fun removeSuccessTopic() {
        guideState = GuideState.SuccessAllTopics(_allTopics.value)
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

    fun getOnMoreInfoTopic(): Guide.Topic {
        if (guideState is GuideState.SuccessTopic) {
            return (guideState as GuideState.SuccessTopic).data
        }
        return Guide.Topic()
    }

    fun getAllTopics(): List<Guide.AllTopics> {
        if (guideState is GuideState.SuccessAllTopics) {
            return (guideState as GuideState.SuccessAllTopics).data
        }
        return emptyList()
    }
}