package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieDetailEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieDetailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseGetMovieDetails(
    private val repository: MovieRepository
) {
    fun getMovieById(id: String): Flow<MovieDetailEntity> =
        repository.getMovieById(id).map { response ->
            TransformerMovieDetailEntity.transform(response)
        }
}