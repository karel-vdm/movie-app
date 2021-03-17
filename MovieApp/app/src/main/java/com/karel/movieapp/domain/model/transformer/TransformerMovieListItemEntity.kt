package com.karel.movieapp.domain.model.transformer

import com.karel.movieapp.data.api.model.MovieSearchDto
import com.karel.movieapp.data.database.model.MovieListItemModel
import com.karel.movieapp.domain.model.MovieListItemEntity

object TransformerMovieListItemEntity {

    fun transform(dto: MovieSearchDto) = MovieListItemEntity(
        id = dto.imdbID ?: String(),
        title = dto.Title ?: String(),
        poster = dto.Poster ?: String(),
        type = dto.Type ?: String(),
        year = dto.Year ?: String()
    )

    fun transformDto(dto: MovieSearchDto) = MovieListItemModel(
        id = dto.imdbID ?: String(),
        title = dto.Title ?: String(),
        poster = dto.Poster ?: String(),
        type = dto.Type ?: String(),
        year = dto.Year ?: String()
    )

    fun transform(entity: MovieListItemEntity) = MovieListItemModel(
        id = entity.id,
        title = entity.title,
        poster = entity.poster,
        type = entity.type,
        year = entity.year
    )

    fun transform(model: MovieListItemModel) = MovieListItemEntity(
        id = model.id,
        title = model.title,
        poster = model.poster,
        type = model.type,
        year = model.year
    )
}