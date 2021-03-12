package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMovieResponseDto
import com.karel.movieapp.domain.model.MovieEntity

object TransformerMovieEntity {

    fun transform(dto: GetMovieResponseDto) = MovieEntity(
        imdbId = dto.imdbID ?: String(),
        title = dto.Title ?: String(),
        poster = dto.Poster ?: String(),
        type = dto.Type ?: String(),
        year = dto.Year ?: String()
    )
}