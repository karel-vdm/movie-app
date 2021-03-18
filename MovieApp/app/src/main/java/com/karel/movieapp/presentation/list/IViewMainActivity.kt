package com.karel.movieapp.presentation.list

interface IViewMainActivity {

    fun addMoviesToView(movies: List<MovieListItemViewModel>)

    fun renderErrorView(error: String?)

    fun hideErrorView()

    fun renderLoadingView()

    fun hideLoadingView()

    fun clearMoviesFromView()
}