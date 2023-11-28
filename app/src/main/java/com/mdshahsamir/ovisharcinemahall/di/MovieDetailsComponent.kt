package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface MovieDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailsComponent
    }

    fun inject(fragment: MovieDetailsFragment)
}