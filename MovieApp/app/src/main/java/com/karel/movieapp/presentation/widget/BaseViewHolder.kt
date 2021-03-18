package com.karel.movieapp.presentation.widget

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindView(viewModel: IAdapterItemViewModel)
}