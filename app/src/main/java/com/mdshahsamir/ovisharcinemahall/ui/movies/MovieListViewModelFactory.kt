package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieListViewModelFactory(private val repo: MovieListRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}