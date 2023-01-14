package com.example.topheadlines.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

        binding.rvHeadlines.adapter = headlinesAdapter


        setObserver()
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
                            binding.progressIndicator.visibility = View.GONE
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressIndicator.visibility = View.VISIBLE
        binding.rvHeadlines.visibility = View.GONE
    }

    private fun showList(articlesList: List<Article>) {
        binding.progressIndicator.visibility = View.GONE
        binding.rvHeadlines.visibility = View.VISIBLE

        headlinesAdapter.updateList(articlesList)
        headlinesAdapter.notifyItemRangeChanged(0, articlesList.lastIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}