package com.karel.movieapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karel.movieapp.domain.usecase.UseCaseGetMovieById
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val useCaseGetMovieById: UseCaseGetMovieById
) : ViewModel() {

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _movie = MutableLiveData<MovieDetailItemViewModel>()
    val movie: LiveData<MovieDetailItemViewModel> get() = _movie

    fun getMovieById(id: String) {
        viewModelScope.launch {
            useCaseGetMovieById.getMovieById(id)
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

