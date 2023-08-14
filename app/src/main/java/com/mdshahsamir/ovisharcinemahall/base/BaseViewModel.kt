package com.mdshahsamir.ovisharcinemahall.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean> = _isDataLoading

    protected val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> = _showMessage
}