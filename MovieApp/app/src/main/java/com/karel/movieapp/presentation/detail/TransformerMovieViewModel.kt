package com.karel.movieapp.presentation.detail

import com.karel.movieapp.domain.model.MovieDetailEntity

object TransformerMovieViewModel {

    fun transform(entity: MovieDetailEntity) = MovieDetailItemViewModel(
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