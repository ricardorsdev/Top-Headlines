package com.example.topheadlines.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable
