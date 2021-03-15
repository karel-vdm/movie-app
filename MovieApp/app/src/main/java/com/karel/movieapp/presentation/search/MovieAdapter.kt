package com.karel.movieapp.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karel.movieapp.databinding.MovieItemBinding

class MovieAdapter(private val onClickListener: (String) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var items: MutableList<MovieSearchViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: MovieItemBinding = MovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBindView(items[position])
    }

    fun addItems(itemsToAdd: List<MovieSearchViewModel>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, itemsToAdd))
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(itemsToAdd)
    }

    private class DiffCallback(
        private val oldList: List<MovieSearchViewModel>,
        private val newList: List<MovieSearchViewModel>
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

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val onClickListener: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBindView(viewModel: MovieSearchViewModel) {
        binding.movieItemTitle.text = viewModel.title
        binding.movieItemYear.text = viewModel.year
        binding.movieItemType.text = viewModel.type

        Glide.with(itemView.context)
            .load(viewModel.poster)
            .fitCenter()
            .into(binding.movieItemPoster)

        binding.root.setOnClickListener {
            onClickListener.invoke(viewModel.id)
        }
    }

}