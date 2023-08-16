package com.mdshahsamir.ovisharcinemahall.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SharedViewModelFactory(private val repo: SharedRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
