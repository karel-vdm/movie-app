package com.karel.movieapp.domain.model

data class MovieListEntity(
    val page: Int = 0,
    val totalResultsLoaded: Int = 0,
    val currentPosition: Int = 0,
    val searchTerm: String = String(),
    val totalResults: String = String(),
    val movies: List<MovieListItemEntity> = emptyList()
)