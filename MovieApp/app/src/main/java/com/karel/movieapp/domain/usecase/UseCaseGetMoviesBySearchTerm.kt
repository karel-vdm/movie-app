package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MoviesEntity
import com.karel.movieapp.domain.model.transformer.TransformerMoviesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseGetMoviesBySearchTerm(
    private val repository: MovieRepository
) {

    fun getMoviesBySearchTerm(searchTerm: String, page: Int): Flow<MoviesEntity> =
        repository.getMoviesBySearchTerm(searchTerm, page).map { response ->
            TransformerMoviesEntity.transform(response)
        }

}