package com.karel.movieapp.presentation.list

import com.karel.movieapp.domain.model.MovieListEntity

object TransformerMovieListViewModel {

    fun transform(viewModel: MovieListViewModel) = MovieListEntity(
        page = viewModel.page,
        totalResultsLoaded = viewModel.totalResultsLoaded,
        currentPosition = viewModel.currentPosition,
        searchTerm = viewModel.searchTerm.value ?: ""
    )
}