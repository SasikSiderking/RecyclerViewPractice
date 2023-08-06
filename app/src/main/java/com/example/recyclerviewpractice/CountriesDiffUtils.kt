package com.example.recyclerviewpractice

import androidx.recyclerview.widget.DiffUtil

class CountriesDiffUtils : DiffUtil.ItemCallback<CountryItem>() {
    override fun areItemsTheSame(oldItem: CountryItem, newItem: CountryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CountryItem, newItem: CountryItem): Boolean {
        return oldItem == newItem
    }
}