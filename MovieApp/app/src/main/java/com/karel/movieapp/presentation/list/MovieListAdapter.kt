package com.karel.movieapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karel.movieapp.databinding.MovieItemBinding
import com.karel.movieapp.databinding.MovieItemShimmerBinding
import com.karel.movieapp.presentation.widget.ViewHolderShimmer


private const val MOVIE = 0
private const val SHIMMER = 1

class MovieAdapter(private val onClickListener: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<MovieListItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == MOVIE) {
            val binding: MovieItemBinding = MovieItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            MovieListItemViewHolder(binding, onClickListener)
        } else {
            val binding: MovieItemShimmerBinding = MovieItemShimmerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderShimmer(binding)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].isInShimmerState) {
            return SHIMMER
        }
        return MOVIE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieListItemViewHolder) {
            holder.onBindView(items[position])
        }
    }

    fun addItems(itemsToAdd: List<MovieListItemViewModel>, isLastPage: Boolean) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, itemsToAdd))
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(itemsToAdd)
        if (!isLastPage) {
            items.add(MovieListItemViewModel(isInShimmerState = true))
        }
    }

    private class DiffCallback(
        private val oldList: List<MovieListItemViewModel>,
        private val newList: List<MovieListItemViewModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newItem = newList[newItemPosition]
            val oldItem = oldList[oldItemPosition]

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newItem = newList[newItemPosition]
            val oldItem = oldList[oldItemPosition]

            return oldItem == newItem
        }
    }

}