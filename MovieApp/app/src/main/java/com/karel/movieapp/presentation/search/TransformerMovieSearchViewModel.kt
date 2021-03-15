package com.karel.movieapp.presentation.search

import com.karel.movieapp.domain.model.MovieSearchEntity
import com.karel.movieapp.presentation.search.MovieSearchViewModel

object TransformerMovieSearchViewModel {

    fun transform(entity: MovieSearchEntity) =
        MovieSearchViewModel(
            id = entity.id,
            title = entity.title,
            poster = entity.poster,
            year = entity.year,
            type = entity.type
        )

}