package com.karel.movieapp.presentation.widget

abstract class PagingAdapter(
    private val items: MutableList<IAdapterItemViewModel> = mutableListOf(),
    onClickListener: (IAdapterItemViewModel) -> Unit = {}
) : ShimmerAdapter(items, onClickListener) {

    var bindToViewHolderListener: (Int) -> Unit = {}

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bindToViewHolderListener.invoke(position)
        super.onBindViewHolder(holder, position)
    }

    fun isShimmerView(position: Int): Boolean {
        val item = items.getOrNull(position)
        return item is ShimmerItemViewModel
    }

    fun isEmptyStateView(position: Int): Boolean {
        val item = items.getOrNull(position)
        return item is EmptyStateViewModel
    }
}
