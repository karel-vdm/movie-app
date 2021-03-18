package com.karel.movieapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class PagingRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val scrollOffset = 3
    private var isLoading = false
    private val pageInfo = PagingInfoViewModel()

    var pageListener: () -> Unit = {}

    fun init(pagingAdapter: PagingAdapter) {
        adapter = pagingAdapter
        (adapter as? PagingAdapter)?.bindToViewHolderListener = { currentPosition ->
            tryPage(pageInfo, currentPosition)
        }
    }

    fun addItems(pageInfo: PagingInfoViewModel, items: List<IAdapterItemViewModel>) {
        (adapter as? PagingAdapter)?.addItems(items)
        if (!pageInfo.isLastPage && items.isNotEmpty()) {
            (adapter as? PagingAdapter)?.addShimmer()
        }
        isLoading = false
    }

    fun addShimmer() {
        if ((adapter as? PagingAdapter)?.hasShimmer() == false) {
            (adapter as? PagingAdapter)?.addShimmer()
        }
    }

    fun clearItems() {
        (adapter as? PagingAdapter)?.clearItems()
    }

    private fun tryPage(pageInfo: PagingInfoViewModel, currentPosition: Int) {
        if (shouldPage(pageInfo, currentPosition)) {
            isLoading = true
            onPaged(pageInfo)
        }
    }

    private fun onPaged(pageInfo: PagingInfoViewModel) {
        pageInfo.currentPage++
        pageListener.invoke()
    }

    private val itemCount: Int
        get() {
            return adapter?.itemCount ?: 0
        }

    private fun shouldPage(pageInfo: PagingInfoViewModel, currentPosition: Int): Boolean {
        return (!isLoading) &&
                currentPosition + scrollOffset >= itemCount &&
                itemCount < pageInfo.total &&
                !((adapter as? PagingAdapter)?.isShimmerView(currentPosition) ?: false) &&
                !((adapter as? PagingAdapter)?.isEmptyStateView(currentPosition) ?: false)
    }
}
