package com.karel.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.domain.usecase.UseCaseGetMoviesBySearchTerm
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val useCaseGetMoviesBySearchTerm = UseCaseGetMoviesBySearchTerm(
        MovieRepositoryImpl(
            MovieService.create()
        )
    )

    private var page = 1

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _movies = MutableLiveData<List<MovieSearchViewModel>>()
    val movies: LiveData<List<MovieSearchViewModel>> get() = _movies

    fun getMoviesBySearchTerm(searchTerm: String) {
        viewModelScope.launch {
            useCaseGetMoviesBySearchTerm.getMoviesBySearchTerm(searchTerm, page)
                .onStart {
                    _error.value = ""
                    _loading.value = true
                }
                .catch { exception ->
                    _error.value = exception.message ?: "error"
                    _loading.value = false
                }
                .collect { result ->
                    _movies.value = result.movies.map {
                        TransformerMovieSearchViewModel.transform(it)
                    }
                    _loading.value = false
                }
        }
    }

}

data class MovieSearchViewModel(
    val title: String = String(),
    val poster: String = String(),
    val year: String = String(),
    val type: String = String()
)