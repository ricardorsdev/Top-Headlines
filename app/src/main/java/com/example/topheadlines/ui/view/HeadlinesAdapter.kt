package com.example.topheadlines.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.topheadlines.data.model.Article
import com.example.topheadlines.databinding.HeadlineItemViewBinding
import com.example.topheadlines.utils.ImageUtils.setImageFromUrl
import javax.inject.Inject

class HeadlinesAdapter @Inject constructor() :
    RecyclerView.Adapter<HeadlinesAdapter.HeadlinesViewHolder>() {

    private var articlesList: MutableList<Article> = mutableListOf()
    private var itemClickListener: ((Article) -> Unit)? = null

    class HeadlinesViewHolder(
        private val binding: HeadlineItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, itemClickListener: ((Article) -> Unit)?) {
            binding.apply {
                headlineImage.setImageFromUrl(binding.root.context, article.imageUrl)
                headlineTitle.text = article.title
                root.setOnClickListener {
                    itemClickListener?.invoke(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val binding =
            HeadlineItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        holder.bind(articlesList[position], itemClickListener)
    }

    override fun getItemCount() = articlesList.size

    fun updateList(articlesList: List<Article>) {
        this.articlesList = articlesList.toMutableList()
        notifyItemRangeChanged(0, articlesList.lastIndex)
    }

    fun setOnItemClickListener(onItemClicked: (Article) -> Unit) {
        this.itemClickListener = onItemClicked
    }
}