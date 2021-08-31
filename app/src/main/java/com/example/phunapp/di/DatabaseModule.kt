package com.example.phunapp.di

import android.content.Context
import com.example.phunapp.data.PhunDao
import com.example.phunapp.data.PhunDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    //PROVIDES DATABASE
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context?) : PhunDatabase {
        return PhunDatabase.getDatabase(context!!)
    }

    //PROVIDES DAO
    @Provides
    @Singleton
    fun providePhunDao(phunDatabase: PhunDatabase): PhunDao {
        return phunDatabase.phunDao()
    }


}