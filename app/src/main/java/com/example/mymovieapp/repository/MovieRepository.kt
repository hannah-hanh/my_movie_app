package com.example.mymovieapp.repository

import com.example.mymovieapp.model.MovieDetailsResponse
import com.example.mymovieapp.model.MovieListResponse
import com.example.mymovieapp.model.MovieListType

interface MovieRepository {

    suspend fun getMovies(type: MovieListType, page: Int): MovieListResponse

    suspend fun getMovieDetails(movie_id: Int): MovieDetailsResponse

}