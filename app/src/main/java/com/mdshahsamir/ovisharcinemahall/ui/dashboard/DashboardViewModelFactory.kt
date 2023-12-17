package com.mdshahsamir.ovisharcinemahall.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DashboardViewModelFactory(private val repo: DashboardRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
