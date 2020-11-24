package com.noha.moviesadvanced.data.source.network.model

import com.google.gson.annotations.SerializedName

class ActorServer(

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String?
)
