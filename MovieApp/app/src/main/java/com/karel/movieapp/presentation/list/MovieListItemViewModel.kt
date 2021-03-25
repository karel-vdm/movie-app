package com.karel.movieapp.presentation.list

data class MovieListItemViewModel(
    val id: String = String(),
    val title: String = String(),
    val poster: String = String(),
    val year: String = String(),
    val type: String = String(),
    val isInShimmerState: Boolean = false
)