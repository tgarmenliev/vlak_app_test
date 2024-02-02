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
        val title: String = "",
        val subtitle: String = "",
        val image: String = "",
        val content: List<Content> = listOf()
    )

    data class Content(
        val text: String = "",
        val image: String = ""
    )

    data class AllTopics(
        val id: Int = 0,
        val title: String = "",
        val subtitle: String = "",
        val image: String = ""
    )

    data class PhotoResponse(
        @SerializedName("imageUrl")
        val imageUrl: String = ""
    )

}