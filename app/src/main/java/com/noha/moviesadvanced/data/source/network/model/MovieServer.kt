package com.noha.moviesadvanced.data.source.network.model

import com.google.gson.annotations.SerializedName

data class MovieServer(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("vote_average")
    val voteAverage: Double
)