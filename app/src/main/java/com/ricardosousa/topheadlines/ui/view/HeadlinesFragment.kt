package com.ricardosousa.topheadlines.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ricardosousa.topheadlines.R
import com.ricardosousa.topheadlines.data.model.Article
import com.ricardosousa.topheadlines.databinding.FragmentHeadlinesBinding
import com.ricardosousa.topheadlines.ui.viewmodel.NewsViewModel
import com.ricardosousa.topheadlines.utils.BiometricsUtils
import com.ricardosousa.topheadlines.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HeadlinesFragment : Fragment() {

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var headlinesAdapter: HeadlinesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setObserver()
        checkBiometrics()
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
                viewModel.articleList.collect { result ->
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
                            showError(R.string.generic_error_message)
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

    private fun showError(@StringRes errorMessage: Int) {
        binding.apply {
            rvHeadlines.visibility = View.GONE
            cpiLoadingState.visibility = View.GONE
            tvErrorState.visibility = View.VISIBLE
            tvErrorState.text = getString(errorMessage)
        }
    }

    private fun goToArticle(article: Article) {
        val action = HeadlinesFragmentDirections.actionHeadlinesFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    private fun checkBiometrics() {
        if (BiometricsUtils.isBiometricAvailable(requireContext())) {
            BiometricsUtils.showBiometricPrompt(fragment = this, onSuccess = {
                viewModel.fetchHeadlines()
            }, onError = {
                showError(R.string.biometrics_error_message)
            })
        } else {
            viewModel.fetchHeadlines()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}