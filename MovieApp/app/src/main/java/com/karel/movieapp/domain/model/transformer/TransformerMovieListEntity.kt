package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieListModel
import com.karel.movieapp.domain.model.MovieListEntity

object TransformerMovieListEntity {

    fun transform(dto: GetMoviesResponseDto) = MovieListEntity(
        totalResults = dto.totalResults ?: String(),
        movies = dto.Search?.map {
            TransformerMovieListItemEntity.transform(it)
        } ?: emptyList()
    )

    fun transform(entity: MovieListEntity) = MovieListModel(
        page = entity.page,
        totalResultsLoaded = entity.totalResultsLoaded,
        currentPosition = entity.currentPosition,
        searchTerm = entity.searchTerm
    )

    fun transform(model: MovieListModel?) = MovieListEntity(
        page = model?.page ?: 1,
        totalResultsLoaded = model?.totalResultsLoaded ?: 0,
        currentPosition = model?.currentPosition ?: 0,
        searchTerm = model?.searchTerm ?: ""
    )
}