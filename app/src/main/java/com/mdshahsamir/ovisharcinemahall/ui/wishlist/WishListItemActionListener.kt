package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import com.mdshahsamir.ovisharcinemahall.model.Movie

interface WishListItemActionListener {

    fun removeFromWishList(movie: Movie)

    fun onClickMovie(movieId: Int)
}