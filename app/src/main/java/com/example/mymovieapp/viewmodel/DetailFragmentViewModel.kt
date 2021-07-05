package com.example.mymovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.model.MovieDetailsResponse
import com.example.mymovieapp.model.MovieListResponse
import com.example.mymovieapp.repository.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var movieItem: MovieListResponse.Result? = null
    val movieDetails = MutableLiveData<MovieDetailsResponse>()

    fun fetchMovies() {
        movieItem?.id?.let { movie_id ->
            viewModelScope.launch {
                val movie = movieRepository.getMovieDetails(movie_id)
                movieDetails.postValue(movie)
            }
        }
    }

}