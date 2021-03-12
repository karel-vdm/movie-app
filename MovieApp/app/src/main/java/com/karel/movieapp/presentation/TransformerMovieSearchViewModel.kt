package com.karel.movieapp.presentation

import com.karel.movieapp.domain.model.MovieSearchEntity

object TransformerMovieSearchViewModel {

    fun transform(entity: MovieSearchEntity) = MovieSearchViewModel(
        title = entity.title,
        poster = entity.poster,
        year = entity.year,
        type = entity.type
    )

}