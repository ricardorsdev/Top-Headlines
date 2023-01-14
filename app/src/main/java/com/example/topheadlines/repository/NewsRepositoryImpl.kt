package com.example.topheadlines.repository

import com.example.topheadlines.data.model.Article
import com.example.topheadlines.data.remote.NewsService
import com.example.topheadlines.utils.DateUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val dataSource: NewsService): NewsRepository {

    override fun getHeadlines(): Flow<List<Article>> {
        //TODO adding getHeadlines hardcoded and including delay;
        // These should be removed later.

        return flow {
            delay(3000)
            emit(dataSource.getHeadlines("bbc-news", "8972067dab374dbf86896893e0c2729a"))
        }.map {
            it.articles.sortedByDescending { article ->
                DateUtils.convertStringDateToTime(article.publishedAt)
            }
        }
    }
}