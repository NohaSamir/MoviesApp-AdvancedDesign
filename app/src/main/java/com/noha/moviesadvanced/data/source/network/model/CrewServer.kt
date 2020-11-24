package com.noha.moviesadvanced.data.source.network.model

import com.google.gson.annotations.SerializedName

class CrewServer(
    @SerializedName("name")
    val name: String,

    @SerializedName("job")
    var job: String
)