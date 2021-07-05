package com.example.mymovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.model.Movie
import com.example.mymovieapp.model.MovieListType
import com.example.mymovieapp.model.MovieSectionModel
import com.example.mymovieapp.repository.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMovies = MutableLiveData(Movie(MovieListType.POPULAR))
    private val _topRatedMovies = MutableLiveData(Movie(MovieListType.TOP_RATED))
    private val _nowPlayingMovies = MutableLiveData(Movie(MovieListType.NOW_PLAYING))
    private val _movieSections = MutableLiveData<MutableList<MovieSectionModel>>()
    val movieSection = _movieSections
    val popularMovies = _popularMovies
    val topRatedMovies = _topRatedMovies
    val nowPlayingMovies = _nowPlayingMovies

    fun initMovieSections() {
        _movieSections.value = mutableListOf(
            MovieSectionModel(MovieListType.NOW_PLAYING, "Now Playing"),
            MovieSectionModel(MovieListType.POPULAR, "Popular"),
            MovieSectionModel(MovieListType.TOP_RATED, "Top Rated")
        )
        fetchMovies()
    }

    private fun fetchMovies() {
        _popularMovies.value?.let {
            fetchMovies(it.listType, true)
        }
        _topRatedMovies.value?.let {
            fetchMovies(it.listType, true)
        }
        _nowPlayingMovies.value?.let {
            fetchMovies(it.listType, true)
        }
    }

    private fun fetchMovies(type: MovieListType, firstTimeInit: Boolean = false) {
        if (getLoadingState(type)) return
        val page = when (type) {
            MovieListType.POPULAR -> {
                _popularMovies.value?.currentPage ?: 1
            }
            MovieListType.TOP_RATED -> {
                _topRatedMovies.value?.currentPage ?: 1
            }
            MovieListType.NOW_PLAYING -> {
                _nowPlayingMovies.value?.currentPage ?: 1
            }
        }
        viewModelScope.launch {
            setLoadingState(type, true)
            val responseListData = movieRepository.getMovies(type, page)
            setLoadingState(type, false)
            when (type) {
                MovieListType.POPULAR -> {
                    _popularMovies.value?.let { currentPopularMovie ->
                        _popularMovies.postValue(currentPopularMovie.apply {
                            if (firstTimeInit) {
                                data.clear()
                            }
                            data.addAll(responseListData.results)
                        })
                    }
                }
                MovieListType.TOP_RATED -> {
                    _topRatedMovies.value?.let { currentTopRatedMovie ->
                        _topRatedMovies.postValue(currentTopRatedMovie.apply {
                            if (firstTimeInit) {
                                data.clear()
                            }
                            data.addAll(responseListData.results)
                        })
                    }
                }
                MovieListType.NOW_PLAYING -> {
                    _nowPlayingMovies.value?.let { currentNowPlayingMovie ->
                        _nowPlayingMovies.postValue(currentNowPlayingMovie.apply {
                            if (firstTimeInit) {
                                data.clear()
                            }
                            data.addAll(responseListData.results)
                        })
                    }
                }
            }
        }
    }

    private fun setLoadingState(type: MovieListType, isLoading: Boolean) {
        when (type) {
            MovieListType.POPULAR -> {
                _popularMovies.value?.let {
                    _popularMovies.postValue(it.apply { this.isLoading = isLoading })
                }
            }
            MovieListType.TOP_RATED -> {
                _topRatedMovies.value?.let {
                    _topRatedMovies.postValue(it.apply { this.isLoading = isLoading })
                }
            }
            MovieListType.NOW_PLAYING -> {
                _nowPlayingMovies.value?.let {
                    _nowPlayingMovies.postValue(it.apply { this.isLoading = isLoading })
                }
            }
        }
    }

    private fun getLoadingState(type: MovieListType): Boolean {
        return when (type) {
            MovieListType.POPULAR -> _popularMovies.value?.isLoading ?: true
            MovieListType.TOP_RATED -> _topRatedMovies.value?.isLoading ?: true
            MovieListType.NOW_PLAYING -> _nowPlayingMovies.value?.isLoading ?: true
        }
    }

}