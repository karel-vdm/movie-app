package com.karel.movieapp.presentation.detail

import com.karel.movieapp.domain.model.MovieEntity
import com.karel.movieapp.domain.model.MovieSearchEntity
import com.karel.movieapp.presentation.search.MovieSearchViewModel

object TransformerMovieViewModel {

    fun transform(entity: MovieEntity) = MovieViewModel(
        id = entity.id,
        title = entity.title,
        poster = entity.poster,
        year = entity.year,
        type = entity.type,
        ageRestriction = entity.ageRestriction,
        runtime = entity.runtime,
        rating = entity.rating,
        plot = entity.plot
    )

}