package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist.WishlistFragment
import dagger.Subcomponent

@Subcomponent
interface WishlistComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WishlistComponent
    }

    fun inject(fragment: WishlistFragment)
}