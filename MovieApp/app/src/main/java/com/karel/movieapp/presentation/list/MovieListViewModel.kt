package com.karel.movieapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karel.movieapp.domain.model.MovieListEntity
import com.karel.movieapp.domain.usecase.*
import com.karel.movieapp.presentation.widget.PagedViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

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

    private var _movies = MutableLiveData<List<MovieListItemViewModel>>()
    val movies: LiveData<List<MovieListItemViewModel>> get() = _movies

    private var _isEmptyState = MutableLiveData<Boolean>()
    val isEmptyState: LiveData<Boolean> get() = _isEmptyState

    private var _scrollPosition = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> get() = _scrollPosition

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
                    onGetMoviesResult(result)
                    cancel()
                }
        }
    }

    fun getMovies(searchTerm: String) {
        _searchTerm.value = searchTerm
        clearSavedState()
        getMovies(page)
    }

    fun onViewCreated() {
        if (!hasRestoredState) {
            getSavedState()
            observeMovies()
        }
    }

    fun onPause() {
        cacheCurrentState()
    }

    private fun getSavedState() {
        viewModelScope.launch {
            val result = useCaseGetSavedState.getSavedState()
            setRestoredState(result)
            cancel()
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
                    if (result.isNotEmpty()) {
                        _movies.value = result.map {
                            TransformerMovieListItemViewModel.transform(it)
                        }
                    } else if (result.isEmpty() && movies.value.isNullOrEmpty()) {
                        _isEmptyState.value = true
                        _movies.value = emptyList()
                    }
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
        resetPagingData()
        _isEmptyState.value = false
        _movies.value = emptyList()
        viewModelScope.launch {
            useCaseClearSavedState.clearSavedState()
        }
    }

    private fun setLoadingState() {
        _error.value = String()
        _isEmptyState.value = false
    }

    private fun setErrorState(exception: Throwable) {
        _isEmptyState.value = false
        _error.value = exception.message ?: return
    }

    private fun setRestoredState(result: MovieListEntity?) {
        if (result != null) {
            _searchTerm.value = result.searchTerm
            _scrollPosition.value = result.pagingInfo.currentPosition
            page = result.pagingInfo.page
            pageSize = result.pagingInfo.pageSize
            totalResults = result.pagingInfo.totalResults
            totalResultsLoaded = result.pagingInfo.totalResultsLoaded
            totalPages = result.pagingInfo.totalPages
            currentPosition = result.pagingInfo.currentPosition
        }
    }

    private fun onGetMoviesResult(result: MovieListEntity) {
        if (_movies.value.isNullOrEmpty()) {
            _isEmptyState.value = result.hasNoResults
            _movies.value = emptyList()
        }
        if (result.isSuccess) {
            onPageLoaded(result)
            cacheCurrentState()
        }
    }

}