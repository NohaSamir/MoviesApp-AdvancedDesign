package com.noha.moviesadvanced.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdropPath: String,
    val rate: Float = 0.9f,
    var director: String = "",
    var actors: List<Actor>? = null
) : Parcelable
