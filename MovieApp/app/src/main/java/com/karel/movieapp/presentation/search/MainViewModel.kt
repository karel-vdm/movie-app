package com.karel.movieapp.presentation.search

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

private const val PAGE_LOAD_OFFSET = 3

class MainViewModel : ViewModel() {

    private val useCaseGetMoviesBySearchTerm = UseCaseGetMoviesBySearchTerm(
        MovieRepositoryImpl(
            MovieService.create()
        )
    )

    private var page = 1
    private var totalMoviesLoaded: Int = 0

    private var searchTerm: String = String()

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _movies = MutableLiveData<List<MovieSearchViewModel>>()
    val movies: LiveData<List<MovieSearchViewModel>> get() = _movies

    fun getMoviesBySearchTerm(searchTerm: String) {
        this.searchTerm = searchTerm
        getMovies()
    }

    fun onScroll(currentPosition: Int) {
        if (shouldPage(currentPosition)) {
            page++
            getMovies()
        }
    }

    private fun shouldPage(currentPosition: Int): Boolean {
        return _loading.value == false &&
                currentPosition + PAGE_LOAD_OFFSET >= totalMoviesLoaded
    }

    private fun getMovies() {
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

                    val allMovies = _movies.value?.toMutableList() ?: mutableListOf()
                    allMovies.addAll(
                        result.movies.map {
                            TransformerMovieSearchViewModel.transform(it)
                        }
                    )

                    _movies.value = allMovies

                    totalMoviesLoaded += result.movies.size
                    _loading.value = false
                }
        }
    }

}

data class MovieSearchViewModel(
    val id: String = String(),
    val title: String = String(),
    val poster: String = String(),
    val year: String = String(),
    val type: String = String()
)