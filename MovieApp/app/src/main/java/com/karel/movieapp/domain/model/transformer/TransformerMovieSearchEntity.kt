package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.SearchDto
import com.karel.movieapp.domain.model.MovieSearchEntity

object TransformerMovieSearchEntity {

    fun transform(dto: SearchDto) = MovieSearchEntity(
        id = dto.imdbID ?: String(),
        title = dto.Title ?: String(),
        poster = dto.Poster ?: String(),
        type = dto.Type ?: String(),
        year = dto.Year ?: String()
    )
}