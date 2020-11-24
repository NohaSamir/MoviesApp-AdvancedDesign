package com.noha.moviesadvanced.data.source.network.model

import com.google.gson.annotations.SerializedName

class MoviesResponse(
    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("results")
    val results: List<MovieServer>,

    @SerializedName("total_results")
    val totalResults: Int = 0,

    @SerializedName("total_pages")
    val totalPages: Int = 0
)