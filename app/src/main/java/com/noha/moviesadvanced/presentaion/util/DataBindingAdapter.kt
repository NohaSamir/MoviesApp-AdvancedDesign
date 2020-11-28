package com.noha.moviesadvanced.presentaion.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.noha.moviesadvanced.Constant
import com.noha.moviesadvanced.domain.model.Actor
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.presentaion.adapter.ActorsAdapter
import com.noha.moviesadvanced.presentaion.adapter.MovieAdapter


@BindingAdapter("loadImageUrl")
fun loadImage(imageView: ImageView, url: String?) {

    if (!url.isNullOrBlank()) {
        Glide.with(imageView)
            .load(Constant.IMAGE_BASE_URL + url)
            .into(imageView)
    }
}


@BindingAdapter("actorsList")
fun bindActorList(recyclerView: RecyclerView, list: List<Actor>?) {
    list?.let { recyclerView.adapter = ActorsAdapter(it) }
}

@BindingAdapter("moviesList")
fun bindMoviesList(recyclerView: RecyclerView, list: List<Movie>?) {
    list?.let { (recyclerView.adapter as MovieAdapter).submitList(list) }
}