package com.noha.moviesadvanced.data.source.network.model

import com.google.gson.annotations.SerializedName

class CastResponse(
    @SerializedName("cast")
    val cast: List<ActorServer>,

    @SerializedName("crew")
    val crew: List<CrewServer>
)