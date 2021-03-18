package com.karel.movieapp.data.api.model

import com.google.gson.annotations.SerializedName

data class GetMoviesResponseDto(

    @SerializedName("Response")
    val Response: String? = null,

    @SerializedName("Search")
    val Search: List<MovieSearchDto>? = null,

    @SerializedName("totalResults")
    val totalResults: String? = null,

    @SerializedName("Error")
    val Error: String? = null
)

