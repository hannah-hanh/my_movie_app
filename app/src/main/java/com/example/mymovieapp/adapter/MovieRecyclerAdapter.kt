package com.example.mymovieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.LayoutMovieHeaderItemBinding
import com.example.mymovieapp.databinding.LayoutMovieHorizontalListItemBinding
import com.example.mymovieapp.model.MovieListResponse
import com.example.mymovieapp.model.MovieListType
import com.example.mymovieapp.model.MovieSectionModel
import com.example.mymovieapp.network.MovieService
import com.jama.carouselview.CarouselView
import com.squareup.picasso.Picasso

class MovieRecyclerAdapter(
    private val context: Context,
    val onMovieItemClick: (item: MovieListResponse.Result) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<MovieSectionModel> = mutableListOf()
    private var nowPlayingMovieItems = mutableListOf<MovieListResponse.Result>()
    private var popularMovieItems = mutableListOf<MovieListResponse.Result>()
    private var topRatedMovieItems = mutableListOf<MovieListResponse.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SectionType.HEADER.type -> MovieHeaderViewHolder(
                LayoutMovieHeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMovieItemClick
            )
            else -> MovieListViewHolder(
                context,
                LayoutMovieHorizontalListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMovieItemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position].listType) {
            MovieListType.NOW_PLAYING -> (holder as? MovieHeaderViewHolder)?.bind(
                nowPlayingMovieItems
            )
            MovieListType.POPULAR -> (holder as? MovieListViewHolder)?.bind(
                items[position],
                popularMovieItems
            )
            MovieListType.TOP_RATED -> (holder as? MovieListViewHolder)?.bind(
                items[position],
                topRatedMovieItems
            )
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int) = when (items[position].listType) {
        MovieListType.NOW_PLAYING -> SectionType.HEADER.type
        else -> SectionType.HORIZONTAL_LIST.type
    }

    fun updateSection(movieSections: MutableList<MovieSectionModel>) {
        items = movieSections
        notifyDataSetChanged()
    }

    fun updateSectionContent(type: MovieListType, movies: List<MovieListResponse.Result>) {
        when (type) {
            MovieListType.NOW_PLAYING -> {
                nowPlayingMovieItems = movies.toMutableList()
                notifyItemChanged(0)
            }
            MovieListType.POPULAR -> {
                popularMovieItems = movies.toMutableList()
                notifyItemChanged(1)
            }
            MovieListType.TOP_RATED -> {
                topRatedMovieItems = movies.toMutableList()
                notifyItemChanged(2)
            }
        }
    }

    class MovieHeaderViewHolder(
        itemViewBinding: LayoutMovieHeaderItemBinding,
        private val onMovieItemClick: (item: MovieListResponse.Result) -> Unit
    ) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        private val moviePageView: CarouselView = itemViewBinding.moviePageView

        fun bind(
            movieItems: List<MovieListResponse.Result>
        ) {
            moviePageView.apply {
                size = movieItems.size
                resource = R.layout.layout_carousel_item
                setCarouselViewListener { view, position ->
                    val ivMovieContent = view.findViewById<ImageView>(R.id.ivMovieContent)
                    val tvMovieName = view.findViewById<TextView>(R.id.tvMovieName)
                    val movieItem = movieItems[position]
                    Picasso
                        .get()
                        .load(MovieService.MOVIE_IMAGE_ENDPOINT + movieItem.backdrop_path)
                        .into(ivMovieContent)
                    tvMovieName.text = "${movieItem.original_title}"

                    view.setOnClickListener {
                        onMovieItemClick.invoke(movieItem)
                    }
                }
                autoPlay = true
                autoPlayDelay = 3000
                scaleOnScroll = true
                hideIndicator(true)
                show()
            }
        }

    }

    class MovieListViewHolder(
        context: Context,
        itemViewBinding: LayoutMovieHorizontalListItemBinding,
        onMovieItemClick: (item: MovieListResponse.Result) -> Unit
    ) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        private val tvSectionName = itemViewBinding.tvSectionName
        private val rvMovie = itemViewBinding.rvMovie
        private val rvAdapter = HorizontalMovieRecyclerAdapter(onMovieItemClick)
        private val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        fun bind(movie: MovieSectionModel, movieItems: List<MovieListResponse.Result>) {
            tvSectionName.text = movie.header
            rvMovie.layoutManager = layoutManager
            rvMovie.adapter = rvAdapter
            rvAdapter.updateMovieList(movieItems.toMutableList())
        }

    }

    enum class SectionType(val type: Int) {
        HEADER(0),
        HORIZONTAL_LIST(1)
    }

}