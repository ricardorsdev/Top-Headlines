package com.example.topheadlines.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.topheadlines.data.model.Article
import com.example.topheadlines.databinding.FragmentArticleBinding
import com.example.topheadlines.utils.ImageUtils.setImageFromUrl


class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleFragmentArgs by navArgs()
    lateinit var article: Article


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = args.article
        setupView()
    }

    private fun setupView() {
        binding.apply {
            ivArticle.setImageFromUrl(binding.root.context, article.imageUrl)
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvContent.text = article.content

            if (!article.url.isNullOrEmpty()) {
                btnGoToBrowser.visibility = View.VISIBLE
                btnGoToBrowser.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(article.url)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}