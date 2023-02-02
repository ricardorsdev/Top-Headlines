package com.ricardosousa.topheadlines.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardosousa.topheadlines.data.model.Article
import com.ricardosousa.topheadlines.repository.NewsRepository
import com.ricardosousa.topheadlines.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var _articleList =
        MutableStateFlow<NetworkResult<List<Article>>>(NetworkResult.Loading)
    val articleList: StateFlow<NetworkResult<List<Article>>> = _articleList

    fun fetchHeadlines() {
        viewModelScope.launch {
            _articleList.value = NetworkResult.Loading

            newsRepository.getHeadlines().catch {
                it.printStackTrace()
                _articleList.value = NetworkResult.Failure(it.message)
            }.collect { articles ->
                _articleList.value = NetworkResult.Success(articles)
            }
        }
    }
}