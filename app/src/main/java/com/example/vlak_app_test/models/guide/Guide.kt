package com.example.vlak_app_test.models.guide

import com.google.gson.annotations.SerializedName


class Guide {
    data class GuideTable(
        val title: String,
        val shortDescription: String,
        val description: String,
        val image: Int
    )

    data class Topic(
        val title: String,
        val subtitle: String,
        val content: List<Content>
    )

    data class Content(
        val text: String,
        val image: String
    )

    data class AllTopics(
        val titles: List<String>,
        val subtitles: List<String>,
        val images: List<String>
    )

    data class PhotoResponse(
        @SerializedName("imageUrl")
        val imageUrl: String
    )

}