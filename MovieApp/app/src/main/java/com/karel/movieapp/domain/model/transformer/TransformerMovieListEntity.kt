package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieListState
import com.karel.movieapp.domain.model.MovieListEntity

object TransformerMovieListEntity {

    fun transform(dto: GetMoviesResponseDto) = MovieListEntity(
        isSuccess = dto.Response?.toBoolean() ?: false,
        pagingInfo = TransformerPagingInfoEntity.transform(dto),
        movies = dto.Search?.map {
            TransformerMovieListItemEntity.transform(it)
        } ?: emptyList()
    )

    fun transform(entity: MovieListEntity) = MovieListState(
        page = entity.pagingInfo.page,
        pageSize = entity.pagingInfo.pageSize,
        totalResults = entity.pagingInfo.totalResults,
        totalResultsLoaded = entity.pagingInfo.totalResultsLoaded,
        currentPosition = entity.pagingInfo.currentPosition,
        searchTerm = entity.searchTerm
    )

    fun transform(model: MovieListState) = MovieListEntity(
        pagingInfo = TransformerPagingInfoEntity.transform(model),
        searchTerm = model.searchTerm
    )
}