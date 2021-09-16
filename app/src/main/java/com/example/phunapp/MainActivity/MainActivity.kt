package com.example.phunapp.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.phunapp.R
import com.example.phunapp.adapters.PhunListAdapter
import com.example.phunapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val phunViewModel: PhunViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PhunListAdapter()
        binding.recyclerView.adapter = adapter

        phunViewModel.getPhunItems()
        //GET LIVEDATA LIST OF ALL ITEMS
        phunViewModel.phunList.observe(this){ phunList ->
            phunList.let{ adapter.submitList(it)}
        }
    }
}