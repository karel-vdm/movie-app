package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.database.MovieDatabase
import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseGetSavedState(
    private val repository: MovieRepository
) {

    fun getSavedState(): Flow<MovieListEntity>? = repository.getSavedState()?.map {
        TransformerMovieListEntity.transform(it)
    }
}