package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val repository: MovieListRepository) : BaseViewModel() {

    val movieList = repository.fetchTopRatedMovies().cachedIn(viewModelScope)

    fun addMovieToWishList(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertMovieToDB(movie)
            }
        }
    }

    fun removeFromWishList(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteMovieFromDB(movie)
            }
        }
    }
}

