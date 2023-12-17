package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepository
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepositoryIml
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class DashboardModule {

    @Binds
    abstract fun bindDashboardRepository(
        dashboardRepositoryImpl: DashboardRepositoryIml
    ): DashboardRepository
}