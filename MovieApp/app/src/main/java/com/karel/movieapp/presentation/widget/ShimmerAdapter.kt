package com.karel.movieapp.presentation.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private val SHIMMER_VIEW = 2

abstract class ShimmerAdapter(
    private val items: MutableList<IAdapterItemViewModel> = mutableListOf(),
    itemClickListener: (IAdapterItemViewModel) -> Unit = {}
) : EmptyStateAdapter(items, itemClickListener) {

    private val isEmptyExceptForShimmer: Boolean
        get() {
            return items.none { it !is ShimmerItemViewModel }
        }

    abstract var shimmerLayoutResource: Int
    abstract var shimmerItemCount: Int

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        addShimmer()
        super.onAttachedToRecyclerView(recyclerView)
    }

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
*//*        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == SHIMMER_VIEW) {
            val view = inflater.inflate(
                shimmerLayoutResource,
                parent,
                false
            )
            ViewHolderShimmer(view)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }*//*
        super.onCreateViewHolder(parent, viewType)
    }*/

    override fun addItems(itemsToAdd: List<IAdapterItemViewModel>) {
        if (itemsToAdd.isEmpty() && isEmptyExceptForShimmer) {
            items.clear()
            super.addItems(itemsToAdd)
        } else if (itemsToAdd.isNotEmpty()) {

            val shimmerIndexList = mutableListOf<Int>()
            items.forEachIndexed { index, it ->
                if (it is ShimmerItemViewModel) {
                    shimmerIndexList.add(index)
                }
            }

            shimmerIndexList.forEachIndexed { index, it ->
                items[it] = itemsToAdd[index]
            }

            if (itemsToAdd.size > shimmerIndexList.size) {
                items.addAll(itemsToAdd.subList(shimmerIndexList.size, itemsToAdd.size))
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is ShimmerItemViewModel) {
            return SHIMMER_VIEW
        }
        return super.getItemViewType(position)
    }

    fun addShimmer() {
        val currentIndex = itemCount
        val endIndex = currentIndex + shimmerItemCount
        for (i in currentIndex until endIndex) {
            items.add(ShimmerItemViewModel())
        }
        notifyItemRangeInserted(currentIndex, shimmerItemCount)
    }

    fun hasShimmer(): Boolean {
        return items.none { it is ShimmerItemViewModel }
    }

}