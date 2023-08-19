package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repo: MovieDetailsRepository) : BaseViewModel() {

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repo.fetchMovieDetails(movieId)
        }
    }
}
