package com.example.mymovieapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mymovieapp.databinding.LayoutDetailFragmentBinding
import com.example.mymovieapp.model.MovieListResponse
import com.example.mymovieapp.network.MovieService
import com.example.mymovieapp.viewmodel.DetailFragmentViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var detailViewModel: DetailFragmentViewModel

    private lateinit var binding: LayoutDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arg ->
            (arg.getSerializable(MOVIE_DATA) as? MovieListResponse.Result)?.let { movieItem ->
                detailViewModel.movieItem = movieItem
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.fetchMovies()
        detailViewModel.movieDetails.observe(viewLifecycleOwner, Observer { movie ->
            Picasso
                .get()
                .load(MovieService.MOVIE_IMAGE_ENDPOINT + movie.poster_path)
                .into(binding.ivPoster)
            binding.tvMovieName.text = movie.title
            val genre = movie.genres.joinToString { it -> "${it.name}" }
            binding.tvGenre.text = genre
            binding.tvReleaseDate.text = movie.release_date
            binding.tvOverview.text = movie.overview
        })
    }

    companion object {

        private const val MOVIE_DATA = "DETAIL_MOVIE_DATA"

        fun createBundleData(movie: MovieListResponse.Result): Bundle {
            return Bundle().apply {
                putSerializable(MOVIE_DATA, movie)
            }
        }

    }
}