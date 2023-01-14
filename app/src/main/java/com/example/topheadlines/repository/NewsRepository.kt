package com.example.topheadlines.repository

import com.example.topheadlines.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getHeadlines(): Flow<List<Article>>
}