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
import com.example.topheadlines.databinding.FragmentHeadlinesBinding
import com.example.topheadlines.ui.viewmodel.NewsViewModel
import com.example.topheadlines.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeadlinesFragment : Fragment() {

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: NewsViewModel by activityViewModels()

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

        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.articleList.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            showLoading(true)
                        }
                        is NetworkResult.Success -> {
                            showLoading(false)
                        }
                        is NetworkResult.Failure -> {
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(shouldShow: Boolean) {
        binding.progressIndicator.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}