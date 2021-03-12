package com.karel.movieapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karel.movieapp.databinding.MovieItemBinding

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var items: MutableList<MovieSearchViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding: MovieItemBinding = MovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBindView(items[position])
    }

    fun addItems(itemsToAdd: List<MovieSearchViewModel>) {
        items.addAll(itemsToAdd)
        notifyDataSetChanged()
    }

}

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(viewModel: MovieSearchViewModel) {
        binding.movieItemTitle.text = viewModel.title
        binding.movieItemYear.text = viewModel.year
        binding.movieItemType.text = viewModel.type

        Glide.with(itemView.context)
            .load(viewModel.poster)
            .fitCenter()
            .into(binding.movieItemPoster)
    }

}