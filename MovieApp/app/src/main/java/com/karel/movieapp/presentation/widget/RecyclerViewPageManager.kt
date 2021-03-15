package com.karel.movieapp.presentation.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val PAGE_LOAD_OFFSET = 3

class RecyclerViewPageManager(
    private val totalResults: Int,
    private val onPageListener: (Int) -> Unit
) {

    private var isLoading = false
    private var page = 1
    private var totalItemsLoaded = 0

    fun onScroll(recyclerView: RecyclerView) {
        if (isLoading) {
            return
        }

        var currentPosition = 0
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            currentPosition = layoutManager.findLastVisibleItemPosition()
        } else if (layoutManager is GridLayoutManager) {
            currentPosition = layoutManager.findLastVisibleItemPosition()
        }

        totalItemsLoaded = recyclerView.adapter?.itemCount ?: 0

        if (currentPosition + PAGE_LOAD_OFFSET >= totalItemsLoaded) {
            isLoading = true
            page++
            onPageListener.invoke(page)
        }
    }

    fun onPageLoadSuccess() {
        isLoading = false
    }

}