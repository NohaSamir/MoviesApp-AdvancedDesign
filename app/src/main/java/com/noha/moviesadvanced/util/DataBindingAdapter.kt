package com.noha.moviesadvanced.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@BindingAdapter("loadImageUrl")
fun loadImage(imageView: ImageView, url: String?) {

    url?.let {
        Glide.with(imageView)
            .load(IMAGE_BASE_URL + url)
            .into(imageView)
    }
}