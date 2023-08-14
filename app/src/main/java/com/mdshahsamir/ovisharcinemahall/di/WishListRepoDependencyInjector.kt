package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.ui.wishlist.WishListRepository
import com.mdshahsamir.ovisharcinemahall.ui.wishlist.WishListRepositoryImpl

class WishListRepoDependencyInjector(private val context: Context) {

    fun getMovieListRepository(): WishListRepository {
        return WishListRepositoryImpl(getMovieDao())
    }

    private fun getMovieDao() = AppDatabase.getDatabase(context).movieDao()
}