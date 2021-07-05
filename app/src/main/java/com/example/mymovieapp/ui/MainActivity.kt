package com.example.mymovieapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.ActivityMainBinding
import com.example.mymovieapp.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewListeners()
        observeBackButtonState()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mainActivityViewModel.setOnDetailFragment(false)
    }

    private fun setViewListeners() {
        binding.ibBack.setOnClickListener {
            mainActivityViewModel.setOnDetailFragment(false)
            findNavController(R.id.nav_host_fragment).popBackStack()
        }
    }

    private fun observeBackButtonState() {
        mainActivityViewModel.isOnDetailFragment.observe(this, { isOnDetailPage ->
            binding.ibBack.visibility = if (isOnDetailPage) View.VISIBLE else View.GONE
        })
    }

}