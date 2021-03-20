package com.karel.movieapp.data.repository

import com.karel.movieapp.data.api.model.GetMovieDetailResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieListItem
import com.karel.movieapp.data.database.model.MovieListState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(searchTerm: String, page: Int): Flow<GetMoviesResponseDto>

    fun getMovieById(id: String): Flow<GetMovieDetailResponseDto>

    suspend fun getSavedState(): MovieListState

    fun getMoviesFromCache(): Flow<List<MovieListItem>>?

    suspend fun saveCurrentState(movie: MovieListState)

    suspend fun deleteMoviesFromCache()

    suspend fun clearSavedState()
}