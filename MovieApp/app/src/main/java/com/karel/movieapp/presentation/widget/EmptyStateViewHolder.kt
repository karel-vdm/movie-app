package com.karel.movieapp.presentation.widget

import com.karel.movieapp.databinding.MovieItemEmptyStateBinding

class EmptyStateViewHolder(private val binding: MovieItemEmptyStateBinding) :
    BaseViewHolder(binding.root) {

    override fun onBindView(viewModel: IAdapterItemViewModel) {
        if (viewModel is EmptyStateViewModel) {
            binding.emptyListText.setText(viewModel.emptyStateStringResource)
        }
    }
}