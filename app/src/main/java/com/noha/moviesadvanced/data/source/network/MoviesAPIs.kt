package com.noha.moviesadvanced.data.source.network

import com.noha.moviesadvanced.BuildConfig
import com.noha.moviesadvanced.data.source.network.model.CastResponse
import com.noha.moviesadvanced.data.source.network.model.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

val movieAPIs: MoviesAPIs by lazy {
    retrofit.create(MoviesAPIs::class.java)
}

interface MoviesAPIs {

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Deferred<MoviesResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCastAndCrewAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey
    ): Deferred<CastResponse>
}