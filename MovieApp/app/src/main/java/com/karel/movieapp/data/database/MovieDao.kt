package com.karel.movieapp.data.database

import androidx.room.*
import com.karel.movieapp.data.database.model.MovieListModel
import com.karel.movieapp.data.database.model.MovieListItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_list")
    fun getSavedState(): Flow<MovieListModel>?

    @Query("SELECT * FROM movie_list_items")
    fun getMovies(): Flow<List<MovieListItemModel>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieListItemModel>)

    @Query("DELETE FROM movie_list_items")
    suspend fun deleteMovies()

    @Query("DELETE FROM movie_list")
    suspend fun deleteSavedState()

}