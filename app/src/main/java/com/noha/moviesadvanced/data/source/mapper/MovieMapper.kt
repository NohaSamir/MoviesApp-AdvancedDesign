package com.noha.moviesadvanced.data.source.mapper

import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.data.source.network.model.MovieServer

fun List<MovieServer>.toDomainModel(): List<Movie> {
    return this.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            poster = it.posterPath ?: "",
            backdropPath = it.backdropPath ?: "",
            rate = (it.voteAverage / 2).toFloat(),
            director = ""
        )
    }
}