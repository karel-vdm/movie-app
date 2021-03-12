package com.karel.movieapp.data.repository

import com.karel.movieapp.data.api.model.GetMovieResponseDto
import com.karel.movieapp.data.api.model.GetMoviesResponseDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesBySearchTerm(searchTerm: String, page: Int): Flow<GetMoviesResponseDto>

    fun getMovieById(imdbId: String): Flow<GetMovieResponseDto>
}