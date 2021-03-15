package com.karel.movieapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.domain.usecase.UseCaseGetMovieById
import com.karel.movieapp.presentation.search.TransformerMovieSearchViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {

    private val useCaseGetMoviesBySearchTerm = UseCaseGetMovieById(
        MovieRepositoryImpl(
            MovieService.create()
        )
    )

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _movie = MutableLiveData<MovieViewModel>()
    val movie: LiveData<MovieViewModel> get() = _movie

    fun getMovieById(id: String) {
        viewModelScope.launch {
            useCaseGetMoviesBySearchTerm.getMovieById(id)
                .onStart {
                    _error.value = ""
                    _loading.value = true
                }
                .catch { exception ->
                    _error.value = exception.message ?: "error"
                    _loading.value = false
                }
                .collect { result ->
                    _movie.value = TransformerMovieViewModel.transform(result)
                    _loading.value = false
                }
        }
    }

}

data class MovieViewModel(
    val id: String = String(),
    val title: String = String(),
    val poster: String = String(),
    val year: String = String(),
    val type: String = String(),
    val ageRestriction: String = String(),
    val runtime: String = String(),
    val rating: String = String(),
    val plot: String = String()
)