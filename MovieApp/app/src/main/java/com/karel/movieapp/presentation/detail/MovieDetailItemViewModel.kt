package com.karel.movieapp.presentation.detail

data class MovieDetailItemViewModel(
    val id: String = String(),
    val title: String = String(),
    val poster: String = String(),
    val year: String = String(),
    val type: String = String(),
    val ageRestriction: String = String(),
    val runtime: String = String(),
    val rating: String = String(),
    val plot: String = String()
)