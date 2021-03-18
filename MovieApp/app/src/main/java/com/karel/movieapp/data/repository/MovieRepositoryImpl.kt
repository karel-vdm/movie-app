package com.karel.movieapp.data.repository

import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.api.model.GetMovieDetailResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.MovieDao
import com.karel.movieapp.data.database.model.MovieListItem
import com.karel.movieapp.data.database.model.MovieList
import com.karel.movieapp.domain.model.transformer.TransformerMovieListItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val API_KEY = "c8eff7dc"

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getMovies(searchTerm: String, page: Int): Flow<GetMoviesResponseDto> = flow {
        val result = movieService.getMovies(API_KEY, searchTerm, page)
        val movies = result.Search?.map {
            TransformerMovieListItemEntity.transformDto(it)
        } ?: emptyList()
        movieDao.addMovies(movies)
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getMovieById(id: String): Flow<GetMovieDetailResponseDto> = flow {
        val movies = movieService.getMovieDetail(API_KEY, id)
        emit(movies)
    }.flowOn(Dispatchers.IO)


    override suspend fun getSavedState(): MovieList {
        return movieDao.getSavedState() ?: MovieList()
    }

    override fun getMoviesFromCache(): Flow<List<MovieListItem>>? {
        return movieDao.getMovies()
    }

    override suspend fun saveCurrentState(movie: MovieList) {
        movieDao.insert(movie)
    }

    override suspend fun deleteMoviesFromCache() {
        movieDao.deleteMovies()
    }

    override suspend fun clearSavedState() {
        movieDao.deleteSavedState()
    }
}