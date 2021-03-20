package com.karel.movieapp.presentation.detail

import androidx.lifecycle.*
import com.karel.movieapp.domain.usecase.*

class MovieDetailViewModelFactory(private val useCaseGetMovieById: UseCaseGetMovieDetails) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            UseCaseGetMovieDetails::class.java
        ).newInstance(
            useCaseGetMovieById
        )
    }

}