package com.example.topheadlines.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.topheadlines.R

object ImageUtils {
    fun ImageView.setImageFromUrl(context: Context, imageUrl: String?) {
        Glide
            .with(context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.headline_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}