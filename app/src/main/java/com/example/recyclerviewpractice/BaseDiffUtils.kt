package com.example.recyclerviewpractice

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtils : DiffUtil.ItemCallback<BaseListItem>() {
    override fun areItemsTheSame(oldItem: BaseListItem, newItem: BaseListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseListItem, newItem: BaseListItem): Boolean {
        return oldItem == newItem
    }
}