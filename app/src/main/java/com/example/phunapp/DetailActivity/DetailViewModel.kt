package com.example.phunapp.DetailActivity

import androidx.lifecycle.ViewModel
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.data.PhunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: PhunRepository) : ViewModel() {

    //GET SINGLE PHUN ITEM WITH ID
    suspend fun getPhunItem(id: Int): PhunModelItem {
        return repository.getPhunItem(id)
    }
}