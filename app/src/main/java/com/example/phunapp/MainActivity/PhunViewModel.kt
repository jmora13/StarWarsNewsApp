package com.example.phunapp.MainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.phunapp.PhunModel.PhunModel
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.data.PhunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
@HiltViewModel
class PhunViewModel @Inject constructor(private val repository: PhunRepository) : ViewModel() {
    //LIVE DATA OF ALL PHUN ITEMS
    val phunList: LiveData<List<PhunModelItem>> = repository.phunList.asLiveData()

    //CALLS REPOSITORY TO MAKE API CALL
    fun getPhunItems(){
        viewModelScope.launch{
            try{
                repository.getPhunResponse()
            } catch (e: IOException){
                Log.d("IOEXCEPTION", e.message.toString())
                return@launch
            } catch(e: HttpException){
                Log.d("HTTPEXCEPTION", e.stackTrace.toString())
                return@launch
            }
        }
    }
}