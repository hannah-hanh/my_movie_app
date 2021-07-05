package com.example.mymovieapp.di

import com.example.mymovieapp.repository.MovieRepository
import com.example.mymovieapp.repository.MovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieServiceModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository (movieRepositoryImp: MovieRepositoryImp): MovieRepository
}