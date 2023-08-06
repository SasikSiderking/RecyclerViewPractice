package com.example.recyclerviewpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recyclerviewpractice.CountriesAdapter.CountryViewHolder
import com.example.recyclerviewpractice.databinding.ItemCountryBinding

class CountriesAdapter : ListAdapter<CountryItem, CountryViewHolder>(CountriesDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        val item = getItem(position)

        with(holder.binding) {
            Glide.with(countryFlag).load(item.flag).centerCrop().into(countryFlag)

            countryId.text = item.id.toString()
            countryName.text = item.name
        }
    }

    class CountryViewHolder(val binding: ItemCountryBinding) : ViewHolder(binding.root)
}