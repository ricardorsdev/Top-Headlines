package com.ricardosousa.topheadlines.repository

import com.ricardosousa.topheadlines.BuildConfig
import com.ricardosousa.topheadlines.data.model.Article
import com.ricardosousa.topheadlines.data.remote.NewsService
import com.ricardosousa.topheadlines.utils.DateUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val dataSource: NewsService): NewsRepository {

    override fun getHeadlines(): Flow<List<Article>?> {
        return flow {
            emit(dataSource.getHeadlines(BuildConfig.SOURCE, BuildConfig.API_KEY))
        }.map {
            it.articles?.sortedByDescending { article ->
                DateUtils.convertStringDateToTime(article.publishedAt)
            }
        }
    }
}