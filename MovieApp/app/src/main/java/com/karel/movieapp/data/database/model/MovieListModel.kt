package com.karel.movieapp.data.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
data class MovieListModel(
    @PrimaryKey
    val id: Int = 1,
    val page: Int = 0,
    val totalResultsLoaded: Int = 0,
    val currentPosition: Int = 0,
    val searchTerm: String = String()
)