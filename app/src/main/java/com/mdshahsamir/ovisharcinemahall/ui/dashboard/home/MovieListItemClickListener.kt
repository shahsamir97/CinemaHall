package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO

interface MovieListItemActionListener {

    fun onClickAddToWishlist(movie: MovieDTO)

    fun onClickRemoveFromWishlist(movie: MovieDTO)

    fun onClickMovie(movieId: Int)
}
