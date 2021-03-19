package com.karel.movieapp

import com.karel.movieapp.data.api.model.GetMovieDetailResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieList
import com.karel.movieapp.data.database.model.MovieListItem
import com.karel.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockMovieRepositoryImpl :
    MovieRepository {
    override fun getMovies(searchTerm: String, page: Int): Flow<GetMoviesResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getMovieById(id: String): Flow<GetMovieDetailResponseDto> {
        return flow {
            emit(
                GetMovieDetailResponseDto(
                    imdbID = "1",
                    Title = "Test",
                    Poster = "",
                    Response = "true",
                    Runtime = "22 min",
                    Year = "2020",
                    imdbRating = "5"
                )
            )
        }
    }

    override suspend fun getSavedState(): MovieList {
        TODO("Not yet implemented")
    }

    override fun getMoviesFromCache(): Flow<List<MovieListItem>>? {
        TODO("Not yet implemented")
    }

    override suspend fun saveCurrentState(movie: MovieList) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMoviesFromCache() {
        TODO("Not yet implemented")
    }

    override suspend fun clearSavedState() {
        TODO("Not yet implemented")
    }

}