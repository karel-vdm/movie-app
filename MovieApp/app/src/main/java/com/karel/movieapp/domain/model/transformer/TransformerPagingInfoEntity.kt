package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieList
import com.karel.movieapp.domain.model.PagingInfoEntity

object TransformerPagingInfoEntity {

    fun transform(dto: GetMoviesResponseDto) = PagingInfoEntity(
        pageSize = dto.Search?.size ?: 0,
        totalResults = dto.totalResults?.toIntOrNull() ?: 0
    )

    fun transform(model: MovieList) = PagingInfoEntity(
        page = model.page,
        pageSize = model.pageSize,
        currentPosition = model.currentPosition,
        totalResults = model.totalResults,
        totalResultsLoaded = model.totalResultsLoaded
    )
}