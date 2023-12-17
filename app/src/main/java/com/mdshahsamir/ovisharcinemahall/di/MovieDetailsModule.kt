package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsRepository
import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class MovieDetailsModule {

    @Binds
    abstract fun bindDashboardRepository(
        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository
}