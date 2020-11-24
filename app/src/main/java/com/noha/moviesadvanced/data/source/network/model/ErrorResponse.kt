package com.noha.moviesadvanced.data.source.network.model

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "status_code")
    val code: Int,
    @Json(name = "status_message")
    val message: String
)