package com.example.mymovieapp.model

data class Movie(val listType: MovieListType) {
    var data = mutableListOf<MovieListResponse.Result>()
    var currentPage: Int = 1
    var isLoading: Boolean = false
}

enum class MovieListType {
    POPULAR,
    TOP_RATED,
    NOW_PLAYING
}