package com.example.topheadlines.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.topheadlines.data.model.Article
import com.example.topheadlines.databinding.FragmentHeadlinesBinding
import com.example.topheadlines.ui.viewmodel.NewsViewModel
import com.example.topheadlines.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HeadlinesFragment : Fragment() {

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: NewsViewModel by activityViewModels()
    @Inject
    lateinit var headlinesAdapter: HeadlinesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setObserver()
    }

    private fun setupAdapter() {
        headlinesAdapter.setOnItemClickListener {
            goToArticle(it)
        }
        binding.rvHeadlines.adapter = headlinesAdapter
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.articleList.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            showLoading()
                        }
                        is NetworkResult.Success -> {
                            result.data?.let {
                                showList(it)
                            }
                        }
                        is NetworkResult.Failure -> {
                            showError()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            tvErrorState.visibility = View.GONE
            rvHeadlines.visibility = View.GONE
            cpiLoadingState.visibility = View.VISIBLE
        }
    }

    private fun showList(articlesList: List<Article>) {
        binding.apply {
            cpiLoadingState.visibility = View.GONE
            tvErrorState.visibility = View.GONE
            rvHeadlines.visibility = View.VISIBLE
        }

        headlinesAdapter.updateList(articlesList)
        headlinesAdapter.notifyItemRangeChanged(0, articlesList.lastIndex)
    }

    private fun showError() {
        binding.apply {
            rvHeadlines.visibility = View.GONE
            cpiLoadingState.visibility = View.GONE
            tvErrorState.visibility = View.VISIBLE
        }
    }

    private fun goToArticle(article: Article) {
        val action = HeadlinesFragmentDirections.actionHeadlinesFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}