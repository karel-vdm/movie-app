package com.karel.movieapp.presentation.widget

import java.io.Serializable

data class PagingInfoViewModel(
    var total: Int = 0,
    var totalPages: Int = 0,
    var pageSize: Int = 0,
    var currentPage: Int = 0
) : Serializable {
    val isLastPage: Boolean
        get() {
            return currentPage + 1 >= totalPages
        }
}