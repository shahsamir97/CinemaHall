package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListFragment
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist.WishlistFragment
import com.mdshahsamir.ovisharcinemahall.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppSubComponents::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun homeComponent(): HomeComponent.Factory

    fun wishlistComponent(): WishlistComponent.Factory
}