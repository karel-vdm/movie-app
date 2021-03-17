package com.karel.movieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karel.movieapp.data.database.model.MovieDetailModel
import com.karel.movieapp.data.database.model.MovieListModel
import com.karel.movieapp.data.database.model.MovieListItemModel

@Database(
    entities = [MovieListModel::class, MovieListItemModel::class, MovieDetailModel::class],
    version = 12,
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