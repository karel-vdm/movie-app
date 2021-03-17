package com.karel.movieapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailModel(

    @PrimaryKey
    val id: String = String()

)