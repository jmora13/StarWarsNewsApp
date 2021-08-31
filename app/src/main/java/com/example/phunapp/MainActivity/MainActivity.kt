package com.example.phunapp.MainActivity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phunapp.R
import com.example.phunapp.adapters.PhunListAdapter
import com.example.phunapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
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

        //GET LIVEDATA LIST OF ALL ITEMS
        phunViewModel.phunList.observe(this){ phunList ->
            phunList.let{ adapter.submitList(it)}
        }

        //CALLS VIEWMODEL TO START API REQUEST
        lifecycleScope.launchWhenCreated {
            try{
                phunViewModel.getPhunItems()
            } catch (e: IOException){
                Log.d("IOEXCEPTION", e.message.toString())
                return@launchWhenCreated
            } catch(e: HttpException){
                Log.d("HTTPEXCEPTION", e.stackTrace.toString())
                return@launchWhenCreated
            }
        }
    }
}