package com.karel.movieapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.domain.usecase.UseCaseGetMoviesBySearchTerm
import com.karel.movieapp.presentation.widget.PagedViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : PagedViewModel() {

    private val useCaseGetMoviesBySearchTerm = UseCaseGetMoviesBySearchTerm(
        MovieRepositoryImpl(
            MovieService.create()
        )
    )

    private var searchTerm: String = String()

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _movies = MutableLiveData<List<MovieSearchViewModel>>()
    val movies: LiveData<List<MovieSearchViewModel>> get() = _movies

    fun getMoviesBySearchTerm(searchTerm: String) {
        this.searchTerm = searchTerm
        getNextPage(1)
    }

    override fun getNextPage(page: Int) {
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
                    _loading.value = false
                    onPageLoaded(result.movies.size)
                }
        }
    }
}