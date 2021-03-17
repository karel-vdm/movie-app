package com.karel.movieapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karel.movieapp.databinding.MovieItemBinding

class MovieAdapter(private val onClickListener: (String) -> Unit) :
    RecyclerView.Adapter<MovieListItemViewHolder>() {

    private var items: MutableList<MovieListItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListItemViewHolder {
        val binding: MovieItemBinding = MovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListItemViewHolder(binding, onClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieListItemViewHolder, position: Int) {
        holder.onBindView(items[position])
    }

    fun addItems(itemsToAdd: List<MovieListItemViewModel>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, itemsToAdd))
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(itemsToAdd)
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

