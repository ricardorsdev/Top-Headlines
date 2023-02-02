package com.ricardosousa.topheadlines.data.remote

import com.ricardosousa.topheadlines.data.model.Headline
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String
    ) : Headline
}