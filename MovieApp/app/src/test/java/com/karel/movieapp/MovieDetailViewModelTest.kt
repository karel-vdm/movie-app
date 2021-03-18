package com.karel.movieapp

import android.icu.text.CaseMap
import com.karel.movieapp.data.api.model.GetMovieDetailResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import com.karel.movieapp.data.database.model.MovieList
import com.karel.movieapp.data.database.model.MovieListItem
import com.karel.movieapp.data.repository.MovieRepository
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.domain.usecase.UseCaseGetMovieById
import com.karel.movieapp.presentation.detail.MovieDetailViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MovieDetailViewModelTest {

    val sut = MovieDetailViewModel(
        UseCaseGetMovieById(
            repository = MovieRepositoryImpl
        )
    )

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    class MockMovieRepositoryImpl: MovieRepository{
        override fun getMovies(searchTerm: String, page: Int): Flow<GetMoviesResponseDto> {
            TODO("Not yet implemented")
        }

        override fun getMovieById(id: String): Flow<GetMovieDetailResponseDto> {
            return flow {
                emit(
                    GetMovieDetailResponseDto(
                        imdbID = 1,
                        Title = "Test",

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
}