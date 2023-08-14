package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import com.mdshahsamir.ovisharcinemahall.model.Movie

interface WishListItemActionListener {

    fun removeFromWishList(movie: Movie, position: Int)

    fun onClickMovie(movieId: Int)
}