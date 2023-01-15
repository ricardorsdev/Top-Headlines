package com.example.topheadlines.repository

import com.example.topheadlines.BuildConfig
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
        return flow {
            emit(dataSource.getHeadlines(BuildConfig.SOURCE, BuildConfig.API_KEY))
        }.map {
            it.articles.sortedByDescending { article ->
                DateUtils.convertStringDateToTime(article.publishedAt)
            }
        }
    }
}