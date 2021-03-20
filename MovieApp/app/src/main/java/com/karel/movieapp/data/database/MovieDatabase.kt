package com.karel.movieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karel.movieapp.data.database.model.MovieDetail
import com.karel.movieapp.data.database.model.MovieListState
import com.karel.movieapp.data.database.model.MovieListItem

@Database(
    entities = [MovieListState::class, MovieListItem::class, MovieDetail::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, MovieDatabase::class.java, "movie-database")
                .fallbackToDestructiveMigration()
                .build()
    }

}