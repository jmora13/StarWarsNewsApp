package com.example.phunapp.data

import com.example.phunapp.PhunModel.PhunModel
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.api.PhunService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhunRepository @Inject constructor(private val phunService: PhunService, private val phunDao: PhunDao) {
    private lateinit var date: String
    //RETURN ALL ITEMS FROM DATABASE
    val phunList: Flow<List<PhunModelItem>> = phunDao.getAllItems()

    //INSERT INDIVIDUAL ITEMS
    private suspend fun insert(item: PhunModelItem){
        phunDao.insert(item)
    }
    //GET SPECIFIC ITEM
    suspend fun getPhunItem(id: Int): PhunModelItem{
        return phunDao.getPhunItem(id)
    }

    //GET RESPONSE OF ITEMS AND ADD TO DATABASE
    suspend fun getPhunResponse(){
        val response = phunService.getPhunItems()

        for(i in 0 until (response.body()?.indices?.count() ?: 0)){
            insert(response.body()?.get(0) ?: break)
        }
    }
}