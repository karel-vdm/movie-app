package com.karel.movieapp.data.api.model

import com.google.gson.annotations.SerializedName

data class GetMovieResponseDto(

    @SerializedName("Actors")
    val Actors: String? = null,

    @SerializedName("Awards")
    val Awards: String? = null,

    @SerializedName("Country")
    val Country: String? = null,

    @SerializedName("Director")
    val Director: String? = null,

    @SerializedName("Genre")
    val Genre: String? = null,

    @SerializedName("Language")
    val Language: String? = null,

    @SerializedName("Metascore")
    val Metascore: String? = null,

    @SerializedName("Plot")
    val Plot: String? = null,

    @SerializedName("Poster")
    val Poster: String? = null,

    @SerializedName("Rated")
    val Rated: String? = null,

    @SerializedName("Ratings")
    val Ratings: List<RatingDto>? = null,

    @SerializedName("Released")
    val Released: String? = null,

    @SerializedName("Response")
    val Response: String? = null,

    @SerializedName("Runtime")
    val Runtime: String? = null,

    @SerializedName("Title")
    val Title: String? = null,

    @SerializedName("Type")
    val Type: String? = null,

    @SerializedName("Writer")
    val Writer: String? = null,

    @SerializedName("Year")
    val Year: String? = null,

    @SerializedName("imdbID")
    val imdbID: String? = null,

    @SerializedName("imdbRating")
    val imdbRating: String? = null,

    @SerializedName("imdbVotes")
    val imdbVotes: String? = null,

    @SerializedName("totalSeasons")
    val totalSeasons: String? = null
)