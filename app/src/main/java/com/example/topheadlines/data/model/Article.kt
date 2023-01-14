package com.example.topheadlines.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String = ""
)
