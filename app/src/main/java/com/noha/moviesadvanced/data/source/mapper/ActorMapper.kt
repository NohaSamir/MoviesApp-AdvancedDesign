package com.noha.moviesadvanced.data.source.mapper

import com.noha.moviesadvanced.domain.model.Actor
import com.noha.moviesadvanced.data.source.network.model.ActorServer

fun List<ActorServer>.toDomainModel(): List<Actor> {
    return this.map {
        Actor(
            name = it.name,
            photoUrl = it.profilePath ?: ""
        )
    }
}