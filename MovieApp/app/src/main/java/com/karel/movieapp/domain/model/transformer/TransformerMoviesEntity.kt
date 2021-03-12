package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.domain.model.MoviesEntity

object TransformerMoviesEntity {

    fun transform(dto: GetMoviesResponseDto) = MoviesEntity(
        totalResults = dto.totalResults ?: String(),
        movies = dto.Search?.map {
            TransformerMovieSearchEntity.transform(it)
        } ?: emptyList()
    )
}