package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MovieDetailsViewModelFactory(private val movieId: Int, private var isAddedToWishlist: Boolean, private val repo: MovieDetailsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(movieId, isAddedToWishlist, repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
