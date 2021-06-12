package com.noha.moviesadvanced.domain.repository

import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.domain.model.Movie

interface MoviesRepository {
    suspend fun getMovieList(pageNum: Int): ResponseWrapper<Any>
    suspend fun getMovieCast(movie: Movie): ResponseWrapper<Any>
}