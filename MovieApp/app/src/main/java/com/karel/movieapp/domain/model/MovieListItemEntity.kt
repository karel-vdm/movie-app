package com.karel.movieapp.domain.model

data class MovieListItemEntity(
    val id: String = String(),
    val title: String = String(),
    val poster: String = String(),
    val type: String = String(),
    val year: String = String()
)