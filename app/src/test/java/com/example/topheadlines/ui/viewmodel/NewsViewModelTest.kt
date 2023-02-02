package com.ricardosousa.topheadlines.ui.viewmodel

import com.ricardosousa.topheadlines.data.model.Article
import com.ricardosousa.topheadlines.repository.NewsRepository
import com.ricardosousa.topheadlines.utils.MainCoroutineRule
import com.ricardosousa.topheadlines.utils.NetworkResult
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class NewsViewModelTest {

    private val repository = mock<NewsRepository>()
    private lateinit var viewModel: NewsViewModel

    @get:Rule
    val rule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = NewsViewModel(repository)
    }

    @Test
    fun `for success response, emission should be Loading and data must be available`() =
        rule.runBlockingTest {
            whenever(repository.getHeadlines()).thenReturn(
                flowOf(mockArticleList)
            )

            val emissions = mutableListOf<NetworkResult<List<Article>>>()
            val job = launch {
                viewModel.articleList.toList(emissions)
            }

            viewModel.fetchHeadlines()

            emissions[1].let {
                assertTrue(it is NetworkResult.Success && mockArticleList == it.data)
            }
            job.cancel()
        }

    @Test
    fun `for loading resource, emission should be Loading`() = rule.runBlockingTest {
        val emissions = mutableListOf<NetworkResult<List<Article>>>()
        val job = launch {
            viewModel.articleList.toList(emissions)
        }

        assertEquals(emissions[0], NetworkResult.Loading)
        job.cancel()
    }

    @Test
    fun `for error resource, emission should be Failure`() = rule.runBlockingTest {
        whenever(repository.getHeadlines()).thenReturn(flow { throw exception })

        val emissions = mutableListOf<NetworkResult<List<Article>>>()
        val job = launch {
            viewModel.articleList.toList(emissions)
        }

        viewModel.fetchHeadlines()

        assertTrue(emissions[1] is NetworkResult.Failure)
        job.cancel()
    }

    companion object {
        val mockArticleList = listOf(
            Article(),
            Article(),
        )
        val exception = Exception()
    }
}