package com.karel.movieapp.presentation.widget

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(
    private val items: MutableList<IAdapterItemViewModel>,
    private val itemClickListener: (IAdapterItemViewModel) -> Unit = {}
) : RecyclerView.Adapter<BaseViewHolder>() {

    abstract fun createViewHolder(parent: ViewGroup): BaseViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return createViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        holder.onBindView(item)
        if (item !is EmptyStateViewModel) {
            holder.itemView.setOnClickListener {
                itemClickListener(items[position])
            }
        }
    }

    open fun addItems(itemsToAdd: List<IAdapterItemViewModel>) {
        val index = if (items.size == 0) items.size else items.size - 1
        items.addAll(itemsToAdd)
        notifyItemRangeChanged(index, itemsToAdd.size - 1)
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

}