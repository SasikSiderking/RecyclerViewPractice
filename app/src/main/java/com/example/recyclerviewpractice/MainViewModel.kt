package com.example.recyclerviewpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.javafaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _countriesStateFlow: MutableStateFlow<List<CountryItem>> =
        MutableStateFlow(emptyList())
    val countriesStateFlow: Flow<List<CountryItem>> = _countriesStateFlow.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val faker = Faker.instance()
            val countries = (1..30).map {
                CountryItem(
                    id = it,
                    flag = faker.country().flag(),
                    name = faker.country().name()
                )
            }

            _countriesStateFlow.emit(countries)
        }
    }


}