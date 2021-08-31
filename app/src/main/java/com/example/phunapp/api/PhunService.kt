package com.example.phunapp.api

import com.example.phunapp.PhunModel.PhunModel
import retrofit2.Response
import retrofit2.http.GET

interface PhunService {

    @GET("phunware-services/dev-interview-homework/master/feed.json")
    suspend fun getPhunItems(): Response<PhunModel>
}
