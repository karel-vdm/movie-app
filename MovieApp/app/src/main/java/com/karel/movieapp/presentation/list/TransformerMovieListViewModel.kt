package com.karel.movieapp.presentation.list

import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.model.PagingInfoEntity

object TransformerMovieListViewModel {

    fun transform(viewModel: MovieListViewModel) = MovieListEntity(
        searchTerm = viewModel.searchTerm.value ?: "",
        pagingInfo = PagingInfoEntity(
            page = viewModel.page,
            pageSize = viewModel.pageSize,
            currentPosition = viewModel.currentPosition,
            totalResults = viewModel.totalResults,
            totalResultsLoaded = viewModel.totalResultsLoaded
        )
    )
}