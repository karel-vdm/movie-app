package com.karel.movieapp.presentation.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import com.karel.movieapp.databinding.MovieItemBinding
import com.karel.movieapp.databinding.MovieItemEmptyStateBinding

private const val EMPTY_VIEW = 1

abstract class EmptyStateAdapter(
    private var items: MutableList<IAdapterItemViewModel>,
    itemClickListener: (IAdapterItemViewModel) -> Unit
) : BaseAdapter(items, itemClickListener) {

    abstract var emptyStateStringResource: Int
    abstract var emptyStateLayoutResource: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == EMPTY_VIEW) {
            val binding: MovieItemEmptyStateBinding = MovieItemEmptyStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            EmptyStateViewHolder(binding)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is EmptyStateViewModel) {
            return EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }

    override fun addItems(itemsToAdd: List<IAdapterItemViewModel>) {
        if (itemsToAdd.isEmpty() && items.isEmpty()) {
            addEmptyStateItem()
        } else {
            if (items.any { it is EmptyStateViewModel }) {
                items.clear()
            }
            super.addItems(itemsToAdd)
        }
    }

    private fun addEmptyStateItem() {
        items.add(
            EmptyStateViewModel(
                emptyStateStringResource = emptyStateStringResource
            )
        )
        notifyDataSetChanged()
    }

}