package com.example.moviestv.di

import com.example.moviestv.data.repository.MoviesTvRepositoryImpl
import com.example.moviestv.domain.repository.MoviesTvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repositoryImpl: MoviesTvRepositoryImpl):MoviesTvRepository

}