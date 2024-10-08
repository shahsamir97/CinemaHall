package com.mdshahsamir.ovisharcinemahall.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val showMessage = MutableLiveData<String>()
}
