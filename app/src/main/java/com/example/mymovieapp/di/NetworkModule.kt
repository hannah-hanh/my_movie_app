package com.example.mymovieapp.di

import com.example.mymovieapp.network.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieServiceDev

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieServiceProd

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @MovieServiceProd
    @Provides
    fun provideMovieService(): MovieService {
        return MovieService.create()
    }

    @Singleton
    @MovieServiceDev
    @Provides
    fun provideMovieServiceDev(): MovieService {
        return MovieService.create(isDevMode = true)
    }
}