package com.example.mymovieapp.network

import com.example.mymovieapp.model.MovieDetailsResponse
import com.example.mymovieapp.model.MovieListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MovieListResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): MovieDetailsResponse

    companion object {

        private const val MOVIE_ENDPOINT = "https://api.themoviedb.org/3/"
        private const val MOVIE_ENDPOINT_DEV = "https://api.themoviedb.org/3/"

        const val MOVIE_IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/original"
        const val MOVIE_API_KEY = "365e7819d41485facaaf8331fa0acc2f"

        const val DEFAULT_LANGUAGE = "en-US"

        fun create(isDevMode: Boolean = false) : MovieService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(if (isDevMode) MOVIE_ENDPOINT_DEV else MOVIE_ENDPOINT)
                .build()
            return retrofit.create(MovieService::class.java)
        }
    }

}