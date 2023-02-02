package com.ricardosousa.topheadlines.repository

import com.ricardosousa.topheadlines.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getHeadlines(): Flow<List<Article>?>
}