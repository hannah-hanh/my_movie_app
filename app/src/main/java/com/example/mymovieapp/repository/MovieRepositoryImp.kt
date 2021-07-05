package com.example.mymovieapp.repository

import com.example.mymovieapp.di.MovieServiceProd
import com.example.mymovieapp.model.MovieDetailsResponse
import com.example.mymovieapp.model.MovieListResponse
import com.example.mymovieapp.model.MovieListType
import com.example.mymovieapp.network.MovieService
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    @MovieServiceProd private val movieService: MovieService): MovieRepository {

    override suspend fun getMovies(
        type: MovieListType,
        page: Int
    ): MovieListResponse {
        return when (type) {
            MovieListType.POPULAR -> {
                movieService.getPopularMovie(MovieService.MOVIE_API_KEY, page, MovieService.DEFAULT_LANGUAGE)
            }
            MovieListType.TOP_RATED -> {
                movieService.getTopRatedMovie(MovieService.MOVIE_API_KEY, page, MovieService.DEFAULT_LANGUAGE)
            }
            MovieListType.NOW_PLAYING -> {
                movieService.getNowPlayingMovie(MovieService.MOVIE_API_KEY, page, MovieService.DEFAULT_LANGUAGE)
            }
        }
    }

    override suspend fun getMovieDetails(movie_id: Int): MovieDetailsResponse {
        return movieService.getMovieDetails(movie_id, MovieService.MOVIE_API_KEY, MovieService.DEFAULT_LANGUAGE)
    }

}