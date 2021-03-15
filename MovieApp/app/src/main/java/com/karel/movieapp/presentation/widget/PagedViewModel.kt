package com.karel.movieapp.presentation.widget

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val PAGE_LOAD_OFFSET = 3

abstract class PagedViewModel : ViewModel() {

    private var page = 1
    private var totalResultsLoaded: Int = 0
    private var loading = false
    private var currentPosition = 0

    abstract fun getNextPage(page: Int)

    protected fun onPageLoaded(itemsLoaded: Int) {
        totalResultsLoaded += itemsLoaded
    }

    fun onScroll(currentPosition: Int) {
        this.currentPosition = currentPosition
        if (shouldPage(currentPosition)) {
            page++
            getNextPage(page)
        }
    }

    private fun shouldPage(currentPosition: Int): Boolean {
        return !loading && currentPosition + PAGE_LOAD_OFFSET >= totalResultsLoaded
    }
}