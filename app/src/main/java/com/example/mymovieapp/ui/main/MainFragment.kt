package com.example.mymovieapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.adapter.MovieRecyclerAdapter
import com.example.mymovieapp.databinding.LayoutMainFragmentBinding
import com.example.mymovieapp.ui.MainActivity
import com.example.mymovieapp.ui.detail.DetailFragment
import com.example.mymovieapp.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var mainFragmentViewModel: MainFragmentViewModel

    private lateinit var binding: LayoutMainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutMainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        observeMovieSection()
        initMovieSectionData()
    }

    private fun setupListAdapter() {
        context?.let {
            binding.rvMovie.layoutManager = LinearLayoutManager(it)
            binding.rvMovie.adapter = MovieRecyclerAdapter(it, onMovieItemClick = { movieItem ->
                findNavController().navigate(
                    R.id.action_mainFragment_to_detailFragment,
                    DetailFragment.createBundleData(movieItem)
                ).also {
                    (activity as? MainActivity)?.let { mainActivity ->
                        mainActivity.mainActivityViewModel.setOnDetailFragment(true)
                    }
                }
            })
        }
    }

    private fun observeMovieSection() {
        mainFragmentViewModel.movieSection.observe(viewLifecycleOwner, {
            (binding.rvMovie.adapter as? MovieRecyclerAdapter)?.updateSection(it)
        })
        mainFragmentViewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            if (it.isLoading) return@observe
            (binding.rvMovie.adapter as? MovieRecyclerAdapter)?.updateSectionContent(
                it.listType,
                it.data
            )
        })
        mainFragmentViewModel.popularMovies.observe(viewLifecycleOwner, {
            if (it.isLoading) return@observe
            (binding.rvMovie.adapter as? MovieRecyclerAdapter)?.updateSectionContent(
                it.listType,
                it.data
            )
        })
        mainFragmentViewModel.topRatedMovies.observe(viewLifecycleOwner, {
            if (it.isLoading) return@observe
            (binding.rvMovie.adapter as? MovieRecyclerAdapter)?.updateSectionContent(
                it.listType,
                it.data
            )
        })
    }

    private fun initMovieSectionData() = mainFragmentViewModel.initMovieSections()

}