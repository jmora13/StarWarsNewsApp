package com.example.phunapp.DetailActivity

import android.util.Log
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.R
import com.example.phunapp.data.PhunRepository
import com.example.phunapp.utilities.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: PhunRepository) : ViewModel() {

    private val _detailItem = MutableLiveData<PhunModelItem>()
    val detailItem: LiveData<PhunModelItem> = _detailItem

    //GET SINGLE PHUN ITEM WITH ID
    fun getPhunItem(id: Int){
        //RETRIEVE ITEM FROM DATABASE USING ID
        viewModelScope.launch {
           val response = try {
                repository.getPhunItem(id)
            } catch (e: IOException) {
                Log.d("IOEXCEPTION", e.message.toString())
                return@launch
            }
            _detailItem.postValue(response)
        }
    }
}