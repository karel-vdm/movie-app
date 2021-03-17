package com.karel.movieapp.presentation.list

import androidx.lifecycle.*
import com.karel.movieapp.domain.usecase.*

class MovieListViewModelFactory(
    private val useCaseGetMovies: UseCaseGetMovies,
    private val useCaseCacheCurrentState: UseCaseCacheCurrentState,
    private val useCaseClearSavedState: UseCaseClearSavedState,
    private val useCaseGetSavedState: UseCaseGetSavedState,
    private val useCaseGetMoviesFromCache: UseCaseGetMoviesFromCache
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            UseCaseGetMovies::class.java,
            UseCaseCacheCurrentState::class.java,
            UseCaseClearSavedState::class.java,
            UseCaseGetSavedState::class.java,
            UseCaseGetMoviesFromCache::class.java
        ).newInstance(
            useCaseGetMovies,
            useCaseCacheCurrentState,
            useCaseClearSavedState,
            useCaseGetSavedState,
            useCaseGetMoviesFromCache
        )
    }

}