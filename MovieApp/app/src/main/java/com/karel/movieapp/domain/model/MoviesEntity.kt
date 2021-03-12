package com.karel.movieapp.domain.model

data class MoviesEntity(
    val totalResults: String = String(),
    val movies: List<MovieSearchEntity> = emptyList()
)