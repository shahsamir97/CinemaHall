package com.mdshahsamir.ovisharcinemahall.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(private val repo: SharedRepository): BaseViewModel() {

    val movieList: Flow<PagingData<Movie>> = repo.fetchTopRatedMovies().cachedIn(viewModelScope).map{ pagingData ->
        pagingData.map { movie ->
            val isAddedToWishlist = wishList.value?.any { it.id == movie.id } ?: false
            val modifiedMovie = movie.copy(isAddedToWishlist = isAddedToWishlist)
            modifiedMovie
        }
    }

    val wishList: LiveData<List<Movie>> = repo.fetchWishListFromDB()

    fun addMovieToWishList(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.insertMovieToDB(movie)
            }
        }
    }

    fun removeFromWishList(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.deleteMovieFromDB(movie)
            }
        }
    }
}