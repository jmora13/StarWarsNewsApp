package com.example.phunapp.di

import com.example.phunapp.data.PhunDao
import com.example.phunapp.data.PhunRepository
import com.example.phunapp.api.PhunService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    //PROVIDES REPOSITORY
    @Provides
    @ViewModelScoped
    fun providesRepo(phunService: PhunService, phunDao: PhunDao): PhunRepository {
        return PhunRepository(phunService, phunDao)
    }
}