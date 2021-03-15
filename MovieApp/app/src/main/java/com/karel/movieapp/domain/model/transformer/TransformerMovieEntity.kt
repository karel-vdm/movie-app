package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.GetMovieResponseDto
import com.karel.movieapp.domain.model.MovieEntity

object TransformerMovieEntity {

    fun transform(dto: GetMovieResponseDto) = MovieEntity(
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