package com.example.recyclerviewpractice

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ClickableViewHolder<T : RecyclerView.ViewHolder>(
    rootView: View,
    private val onClickListener: OnViewHolderClickListener<T>
) : RecyclerView.ViewHolder(rootView) {

    init {
        rootView.setOnClickListener {
            onClickListener.onViewHolderClick(itemView, holder = this as T)
        }
    }
}