package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import com.mdshahsamir.ovisharcinemahall.model.Movie

interface MovieListItemActionListener {

    fun onClickAddToWishlist(movie: Movie)

    fun onClickRemoveFromWishlist(movie: Movie)

    fun onClickMovie(movie: Movie)
}
