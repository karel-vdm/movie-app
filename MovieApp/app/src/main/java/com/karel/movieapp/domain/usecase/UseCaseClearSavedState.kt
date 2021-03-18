package com.karel.movieapp.domain.usecase

import com.karel.movieapp.data.repository.MovieRepository

class UseCaseClearSavedState(
    private val repository: MovieRepository
) {
    suspend fun clearSavedState() {
        repository.clearSavedState()
        repository.deleteMoviesFromCache()
    }
}