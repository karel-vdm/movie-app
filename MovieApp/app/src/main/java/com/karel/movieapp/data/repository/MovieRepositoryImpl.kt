package com.karel.movieapp.data.repository

import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.api.model.GetMovieResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val API_KEY = "c8eff7dc"

class MovieRepositoryImpl(
    private val movieService: MovieService
) : MovieRepository {

    override fun getMoviesBySearchTerm(searchTerm: String, page: Int): Flow<GetMoviesResponseDto> =
        flow {
            val movies = movieService.getMoviesBySearchTerm(API_KEY, searchTerm, page)
            emit(movies)
        }.flowOn(Dispatchers.IO)

    override fun getMovieById(id: String): Flow<GetMovieResponseDto> =
        flow {
            val movies = movieService.getMovieById(API_KEY, id)
            emit(movies)
        }.flowOn(Dispatchers.IO)

}