package com.karel.movieapp.presentation.list

import com.karel.movieapp.domain.model.MovieListItemEntity

object TransformerMovieListItemViewModel {

    fun transform(entity: MovieListItemEntity) = MovieListItemViewModel(
        id = entity.id,
        title = entity.title,
        poster = entity.poster,
        year = entity.year,
        type = entity.type
    )

    fun transform(entity: MovieListItemViewModel) = MovieListItemEntity(
        id = entity.id,
        title = entity.title,
        poster = entity.poster,
        year = entity.year,
        type = entity.type
    )

}