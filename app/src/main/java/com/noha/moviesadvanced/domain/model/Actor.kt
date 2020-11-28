package com.noha.moviesadvanced.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Actor(
    val name: String,
    val photoUrl: String
) : Parcelable