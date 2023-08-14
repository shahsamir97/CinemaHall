package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishListViewModel(private val repo: WishListRepository) : BaseViewModel() {

    private val _wishList = MutableLiveData<List<Movie>>()
    val wishlist: LiveData<List<Movie>>
        get() = _wishList

    fun loadWishList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val wishListMovies = repo.fetchWishListFromDB()
                _wishList.postValue(wishListMovies)
            }
        }
    }

    fun removeFromWishList(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.deleteMovieFromDB(movie)
                loadWishList()
            }
        }
    }
}