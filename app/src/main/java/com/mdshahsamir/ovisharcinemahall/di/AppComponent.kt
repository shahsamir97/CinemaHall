package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import com.mdshahsamir.ovisharcinemahall.MainActivity
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListFragment
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist.WishlistFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: MovieListFragment)

    fun inject(fragment: WishlistFragment)
}