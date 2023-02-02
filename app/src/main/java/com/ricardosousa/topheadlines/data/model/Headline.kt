package com.ricardosousa.topheadlines.data.model

import com.google.gson.annotations.SerializedName

data class Headline(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articles: List<Article>? = listOf()
)