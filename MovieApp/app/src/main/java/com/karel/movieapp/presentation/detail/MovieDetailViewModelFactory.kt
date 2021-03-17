package com.karel.movieapp.presentation.detail

import androidx.lifecycle.*
import com.karel.movieapp.domain.usecase.*

class MovieDetailViewModelFactory(private val useCaseGetMovieById: UseCaseGetMovieById) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            UseCaseGetMovieById::class.java
        ).newInstance(
            useCaseGetMovieById
        )
    }

}