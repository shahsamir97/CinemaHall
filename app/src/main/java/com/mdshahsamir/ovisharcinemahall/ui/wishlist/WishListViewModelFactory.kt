package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class WishListViewModelFactory(private val repo: WishListRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishListViewModel::class.java))
            return WishListViewModel(repo) as T

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}