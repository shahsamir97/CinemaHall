package com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist

import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO

interface WishListItemActionListener {

    fun removeFromWishList(movie: MovieDTO)

    fun onClickMovie(movieId: Int)
}