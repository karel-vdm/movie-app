package com.karel.movieapp.domain.model

data class PagingInfoEntity(
    val page: Int = 0,
    val pageSize: Int = 0,
    val currentPosition: Int = 0,
    val totalResults: Int = 0,
    val totalResultsLoaded: Int = 0
) {
    val totalPages: Int
        get() {
            return if (pageSize != 0 && totalResults != 0) {
                totalResults / pageSize
            } else {
                0
            }
        }
}