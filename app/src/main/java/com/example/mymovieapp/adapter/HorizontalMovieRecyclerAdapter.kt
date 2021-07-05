package com.example.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.databinding.LayoutMovieItemBinding
import com.example.mymovieapp.model.MovieListResponse
import com.example.mymovieapp.network.MovieService
import com.squareup.picasso.Picasso

class HorizontalMovieRecyclerAdapter(private val onMovieItemClick: (item: MovieListResponse.Result) -> Unit) :
    RecyclerView.Adapter<HorizontalMovieRecyclerAdapter.MovieItemViewHolder>() {

    private var items = mutableListOf<MovieListResponse.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            LayoutMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onMovieItemClick
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateMovieList(movies: MutableList<MovieListResponse.Result>) {
        items = movies
        notifyDataSetChanged()
    }

    class MovieItemViewHolder(
        private val itemViewBinding: LayoutMovieItemBinding,
        private val onMovieItemClick: (item: MovieListResponse.Result) -> Unit
    ) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        private val tvMovieName = itemViewBinding.tvMovieName
        private val ivThumbnail = itemViewBinding.ivThumbnail

        fun bind(movie: MovieListResponse.Result) {
            tvMovieName.text = movie.original_title
            Picasso
                .get()
                .load(MovieService.MOVIE_IMAGE_ENDPOINT + movie.poster_path)
                .into(ivThumbnail)
            itemViewBinding.root.setOnClickListener { onMovieItemClick.invoke(movie) }
        }

    }


}