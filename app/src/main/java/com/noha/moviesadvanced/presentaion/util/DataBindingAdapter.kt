package com.noha.moviesadvanced.presentaion.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.noha.moviesadvanced.Constant


@BindingAdapter("loadImageUrl")
fun loadImage(imageView: ImageView, url: String?) {

    if (!url.isNullOrBlank()) {
        Glide.with(imageView)
            .load(Constant.IMAGE_BASE_URL + url)
            .into(imageView)
    }
}