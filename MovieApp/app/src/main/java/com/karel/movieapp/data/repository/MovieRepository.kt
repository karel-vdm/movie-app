package com.karel.movieapp.data.repository

import com.karel.movieapp.data.api.model.GetMovieDetailResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieListItemModel
import com.karel.movieapp.data.database.model.MovieListModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(searchTerm: String, page: Int): Flow<GetMoviesResponseDto>

    fun getMovieById(id: String): Flow<GetMovieDetailResponseDto>

    fun getSavedState(): Flow<MovieListModel>?

    fun getMoviesFromCache(): Flow<List<MovieListItemModel>>?

    suspend fun saveCurrentState(movie: MovieListModel)

    suspend fun deleteMoviesFromCache()

    suspend fun clearSavedState()
}