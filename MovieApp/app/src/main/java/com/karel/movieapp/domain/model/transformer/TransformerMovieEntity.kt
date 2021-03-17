package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMovieDetailResponseDto
import com.karel.movieapp.domain.model.MovieDetailEntity

object TransformerMovieEntity {

    fun transform(dto: GetMovieDetailResponseDto) = MovieDetailEntity(
        id = dto.imdbID ?: String(),
        title = dto.Title ?: String(),
        poster = dto.Poster ?: String(),
        type = dto.Type ?: String(),
        year = dto.Year ?: String(),
        ageRestriction = dto.Rated ?: String(),
        runtime = dto.Runtime ?: String(),
        rating = dto.imdbRating ?: String(),
        plot = dto.Plot ?: String()
    )
}