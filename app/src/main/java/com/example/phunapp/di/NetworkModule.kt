package com.example.phunapp.di

import com.example.phunapp.api.PhunService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    //PROVIDE RETROFIT INSTANCE
    @Provides
    @Singleton
    fun providePhunService(): PhunService {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(PhunService::class.java)
    }
}