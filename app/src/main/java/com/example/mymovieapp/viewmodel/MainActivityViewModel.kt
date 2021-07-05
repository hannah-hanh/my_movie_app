package com.example.mymovieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val _isOnDetailFragment = MutableLiveData(false)
    val isOnDetailFragment = _isOnDetailFragment

    fun setOnDetailFragment(isOnDetailFragment: Boolean) {
        _isOnDetailFragment.value = isOnDetailFragment
    }
}