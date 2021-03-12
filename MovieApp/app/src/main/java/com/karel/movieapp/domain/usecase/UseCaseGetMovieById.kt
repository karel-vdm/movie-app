package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseGetMovieById(
    private val repository: MovieRepository
) {

    fun getMovieById(imdbId: String): Flow<MovieEntity> =
        repository.getMovieById(imdbId).map { response ->
            TransformerMovieEntity.transform(response)
        }

}