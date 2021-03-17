package com.karel.movieapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.model.MovieListItemEntity
import com.karel.movieapp.domain.usecase.*
import com.karel.movieapp.presentation.widget.PagedViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

private const val FIRST_PAGE_INDEX = 1

class MovieListViewModel(
    private val useCaseGetMovies: UseCaseGetMovies,
    private val useCaseCacheCurrentState: UseCaseCacheCurrentState,
    private val useCaseClearSavedState: UseCaseClearSavedState,
    private val useCaseGetSavedState: UseCaseGetSavedState,
    private val useCaseGetMoviesFromCache: UseCaseGetMoviesFromCache
) : PagedViewModel() {

    private var hasRestoredState = false

    private var _searchTerm = MutableLiveData<String>()
    val searchTerm: LiveData<String> get() = _searchTerm

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _movies = MutableLiveData<List<MovieListItemViewModel>>()
    val movies: LiveData<List<MovieListItemViewModel>> get() = _movies

    private var _scrollPosition = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> get() = _scrollPosition

    fun onViewCreated() {
        if (!hasRestoredState) {
            observeMovies()
            observeSavedState()
        }
    }

    fun onStop() {
        cacheCurrentState()
    }

    fun getMovies(searchTerm: String) {
        _searchTerm.value = searchTerm
        clearSavedState()
        getMovies(FIRST_PAGE_INDEX)
    }

    override fun getMovies(page: Int) {
        viewModelScope.launch {
            val searchTerm = _searchTerm.value ?: ""
            useCaseGetMovies.getMoviesBySearchTerm(searchTerm, page)
                .onStart {
                    setLoadingState()
                }
                .catch { exception ->
                    setErrorState(exception)
                }
                .collect { result ->
                    onPageLoaded(result.movies.size)
                }
        }
    }

    private fun observeSavedState() {
        viewModelScope.launch {
            useCaseGetSavedState.getSavedState()
                ?.onStart {
                    setLoadingState()
                }
                ?.catch { exception ->
                    setErrorState(exception)
                }
                ?.collect { result ->
                    if (!hasRestoredState) {
                        // TODO: find a more elegant solution
                        // Flow is probably not the right solution in this case.
                        // Due to time constraints I'm putting this hack in place
                        hasRestoredState = true
                        setRestoredState(result)
                    }
                }
        }
    }

    private fun observeMovies() {
        viewModelScope.launch {
            useCaseGetMoviesFromCache.getMovieListItems()
                ?.onStart {
                    setLoadingState()
                }
                ?.catch { exception ->
                    setErrorState(exception)
                }
                ?.collect { result ->
                    setMoviesLoadedState(result)
                }
        }
    }

    private fun cacheCurrentState() {
        viewModelScope.launch {
            useCaseCacheCurrentState.cacheMovies(
                TransformerMovieListViewModel.transform(this@MovieListViewModel)
            )
        }
    }

    private fun clearSavedState() {
        _movies.value = emptyList()
        page = 1
        currentPosition = 0
        totalResultsLoaded = 0
        viewModelScope.launch {
            useCaseClearSavedState.clearSavedState()
        }
    }

    private fun setLoadingState() {
        _error.value = String()
        _loading.value = true
    }

    private fun setErrorState(exception: Throwable) {
        _error.value = exception.message ?: "error"
        _loading.value = false
    }

    private fun setRestoredState(result: MovieListEntity) {
        _searchTerm.value = result.searchTerm
        currentPosition = result.currentPosition
        totalResultsLoaded = result.totalResultsLoaded
        page = result.page
    }

    private fun setMoviesLoadedState(result: List<MovieListItemEntity>) {
        _movies.value = result.map {
            TransformerMovieListItemViewModel.transform(it)
        }
        _scrollPosition.value = currentPosition
    }
}