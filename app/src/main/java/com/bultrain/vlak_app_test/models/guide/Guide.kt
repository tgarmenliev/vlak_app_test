package com.bultrain.vlak_app_test.models.guide

class Guide {

    data class Topic(
        val title: String = "",
        val subtitle: String = "",
        val image: String = "",
        val content: List<Content> = emptyList()
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

}