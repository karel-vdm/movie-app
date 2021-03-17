package com.karel.movieapp.presentation.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karel.movieapp.databinding.MovieItemBinding

class MovieListItemViewHolder(
    private val binding: MovieItemBinding,
    private val onClickListener: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBindView(viewModel: MovieListItemViewModel) {
        binding.movieItemTitle.text = viewModel.title
        binding.movieItemYear.text = viewModel.year
        binding.movieItemType.text = viewModel.type

        Glide.get(itemView.context).setMemoryCategory(MemoryCategory.LOW)

        Glide.with(itemView.context)
            .load(viewModel.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(binding.movieItemPoster)

        binding.root.setOnClickListener {
            onClickListener.invoke(viewModel.id)
        }
    }

}