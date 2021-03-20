package com.karel.movieapp.domain.model

data class MovieListEntity(
    val isSuccess: Boolean = false,
    val searchTerm: String = String(),
    val pagingInfo: PagingInfoEntity = PagingInfoEntity(),
    val movies: List<MovieListItemEntity> = emptyList()
) {
    val hasNoResults: Boolean
        get() {
            return movies.isEmpty()
        }
}

