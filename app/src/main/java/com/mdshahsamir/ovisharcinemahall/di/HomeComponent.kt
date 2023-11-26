package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListFragment
import dagger.Subcomponent

@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(fragment: MovieListFragment)
}