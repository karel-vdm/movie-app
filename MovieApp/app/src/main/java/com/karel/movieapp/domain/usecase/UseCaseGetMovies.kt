package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.model.transformer.TransformerMovieListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseGetMovies(
    private val repository: MovieRepository
) {

    fun getMoviesBySearchTerm(searchTerm: String, page: Int): Flow<MovieListEntity> =
        repository.getMovies(searchTerm, page).map { response ->
            TransformerMovieListEntity.transform(response)
        }

}