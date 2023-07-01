package com.example.recyclerviewpractice

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.recyclerviewpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {
    private val binding: ActivityMainBinding
        get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    private val adapter = CountriesAdapter()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.countriesRecyclerView.adapter = adapter
        collectCountries()
    }

    private fun collectCountries() {
        lifecycleScope.launch {
            viewModel.countriesStateFlow.collect { countries ->
                adapter.submitList(countries)
            }
        }
    }
}