package com.example.topheadlines.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.topheadlines.R
import com.example.topheadlines.data.model.Article
import com.example.topheadlines.databinding.HeadlineItemViewBinding
import javax.inject.Inject

class HeadlinesAdapter @Inject constructor() :
    RecyclerView.Adapter<HeadlinesAdapter.HeadlinesViewHolder>() {

    private var articlesList: MutableList<Article> = mutableListOf()

    class HeadlinesViewHolder(
        private val binding: HeadlineItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide
                .with(binding.root.context)
                .load(article.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.headline_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.headlineImage)

            binding.headlineTitle.text = article.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val binding =
            HeadlineItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        holder.bind(articlesList[position])
    }

    override fun getItemCount() = articlesList.size

    fun updateList(articlesList: List<Article>) {
        this.articlesList = articlesList.toMutableList()
        notifyItemRangeChanged(0, articlesList.lastIndex)
    }
}