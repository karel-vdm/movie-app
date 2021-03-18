package com.karel.movieapp.presentation.list

import com.karel.movieapp.presentation.widget.IAdapterItemViewModel

data class MovieListItemViewModel(
    val id: String = String(),
    val title: String = String(),
    val poster: String = String(),
    val year: String = String(),
    val type: String = String(),
    val isInShimmerState: Boolean = false
) : IAdapterItemViewModel