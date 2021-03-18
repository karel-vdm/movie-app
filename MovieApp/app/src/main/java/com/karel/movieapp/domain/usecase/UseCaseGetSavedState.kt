package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieListEntity

class UseCaseGetSavedState(
    private val repository: MovieRepository
) {

    suspend fun getSavedState(): MovieListEntity {
        return TransformerMovieListEntity.transform(repository.getSavedState())
    }

}