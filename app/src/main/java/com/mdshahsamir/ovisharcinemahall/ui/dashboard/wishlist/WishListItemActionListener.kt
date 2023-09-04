package com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist

import com.mdshahsamir.ovisharcinemahall.model.Movie

interface WishListItemActionListener {

    fun removeFromWishList(movie: Movie)

    fun onClickMovie(movie: Movie)
}