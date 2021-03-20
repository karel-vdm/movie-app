package com.karel.movieapp.data.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list_state")
data class MovieListState(
    @PrimaryKey
    val id: Int = 1,
    val page: Int = 0,
    val pageSize: Int = 0,
    val totalResults: Int = 0,
    val totalResultsLoaded: Int = 0,
    val currentPosition: Int = 0,
    val searchTerm: String = String()
)