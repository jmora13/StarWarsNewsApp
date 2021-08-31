package com.example.phunapp.MainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.phunapp.PhunModel.PhunModel
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.data.PhunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class PhunViewModel @Inject constructor(private val repository: PhunRepository) : ViewModel() {
    //LIVE DATA OF ALL PHUN ITEMS
    val phunList: LiveData<List<PhunModelItem>> = repository.phunList.asLiveData()

    //CALLS REPOSITORY TO MAKE API CALL
    suspend fun getPhunItems(): Response<PhunModel>? {
        return repository.getPhunResponse()
    }
}