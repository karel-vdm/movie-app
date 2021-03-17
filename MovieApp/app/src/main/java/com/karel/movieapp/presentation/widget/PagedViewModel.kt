package com.karel.movieapp.presentation.widget

import android.util.Log
import androidx.lifecycle.ViewModel

private const val PAGE_LOAD_OFFSET = 3

abstract class PagedViewModel : ViewModel() {

    var page = 1
    var totalResultsLoaded: Int = 0
    var currentPosition = 0
    private var loading = false

    protected abstract fun getMovies(page: Int)

    protected fun onPageLoaded(itemsLoaded: Int) {
        loading = false
        totalResultsLoaded += itemsLoaded
    }

    fun onScroll(currentPosition: Int) {
        Log.d("scroll", "currentPosition $currentPosition")
        this.currentPosition = currentPosition
        if (shouldPage(currentPosition)) {
            loading = true
            page++
            getMovies(page)
            Log.d("scrolled", "currentPosition $currentPosition")
            Log.d("scrolled", "Page $page")
        }
    }

    private fun shouldPage(currentPosition: Int): Boolean {
        return !loading &&
                currentPosition + PAGE_LOAD_OFFSET >= totalResultsLoaded &&
                totalResultsLoaded > 0
    }
}