package com.karel.movieapp.presentation.widget

import androidx.lifecycle.ViewModel
import com.karel.movieapp.domain.model.MovieListEntity

private const val PAGE_LOAD_OFFSET = 3
private const val FIRST_PAGE_INDEX = 1

abstract class PagedViewModel : ViewModel() {

    var page = FIRST_PAGE_INDEX
    var pageSize = 0
    var totalPages = 0
    var totalResults = 0
    var totalResultsLoaded = 0
    var currentPosition = 0
    private var loading = false

    val isLastPage: Boolean
        get() {
            return if (totalResults == 0) {
                false
            } else {
                totalResultsLoaded + pageSize >= totalResults
            }
        }

    protected abstract fun getMovies(page: Int)

    protected fun onPageLoaded(result: MovieListEntity) {
        loading = false
        pageSize = result.movies.size
        totalResults = result.pagingInfo.totalResults
        totalResultsLoaded += result.movies.size
        totalPages = result.pagingInfo.totalPages
    }

    protected fun resetPagingData() {
        page = FIRST_PAGE_INDEX
        pageSize = 0
        totalPages = 0
        totalResults = 0
        totalResultsLoaded = 0
        currentPosition = 0
    }

    fun onScroll(currentPosition: Int) {
        this.currentPosition = currentPosition
        if (shouldPage(currentPosition)) {
            loading = true
            page++
            getMovies(page)
        }
    }

    private fun shouldPage(currentPosition: Int): Boolean {
        return !loading && currentPosition + PAGE_LOAD_OFFSET >= totalResultsLoaded &&
                totalResultsLoaded > 0
    }
}