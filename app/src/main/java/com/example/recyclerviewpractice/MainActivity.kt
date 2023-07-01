package com.example.recyclerviewpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewpractice.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private val binding: ActivityMainBinding
        get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}