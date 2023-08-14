package com.mdshahsamir.ovisharcinemahall.ui.movies

import com.mdshahsamir.ovisharcinemahall.model.Movie

interface MovieListItemActionListener {

    fun onClickAddToWishlist(movie: Movie)

    fun onClickRemoveFromWishlist(movie: Movie)

    fun onClickMovie(movieId: Int)
}