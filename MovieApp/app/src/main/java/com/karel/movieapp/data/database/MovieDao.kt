package com.karel.movieapp.data.database

import androidx.room.*
import com.karel.movieapp.data.database.model.MovieList
import com.karel.movieapp.data.database.model.MovieListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM app_state")
    suspend fun getSavedState(): MovieList?

    @Query("SELECT * FROM movie_list_items")
    fun getMovies(): Flow<List<MovieListItem>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieListItem>)

    @Query("DELETE FROM movie_list_items")
    suspend fun deleteMovies()

    @Query("DELETE FROM app_state")
    suspend fun deleteSavedState()

}