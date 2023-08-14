package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import com.mdshahsamir.ovisharcinemahall.model.Movie

interface WishListRepository {

    fun fetchWishListFromDB(): List<Movie>

    fun deleteMovieFromDB(movie: Movie)
}

class WishListRepositoryImpl(private val movieDao: MovieDao): WishListRepository {

    override fun fetchWishListFromDB(): List<Movie> = movieDao.getAllMovie()

    override fun deleteMovieFromDB(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}