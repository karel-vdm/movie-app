package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieListItemEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieListItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseGetMoviesFromCache(
    private val repository: MovieRepository
) {
    fun getMovieListItems(): Flow<List<MovieListItemEntity>>? =
        repository.getMoviesFromCache()?.map { results ->
            results.map {
                TransformerMovieListItemEntity.transform(it)
            }
        }
}