package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieListEntity

class UseCaseCacheCurrentState(
    private val repository: MovieRepository
) {

    suspend fun cacheMovies(movie: MovieListEntity) {
        repository.saveCurrentState(TransformerMovieListEntity.transform(movie))
    }

}