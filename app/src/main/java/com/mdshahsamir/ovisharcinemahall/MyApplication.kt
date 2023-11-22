package com.mdshahsamir.ovisharcinemahall

import android.app.Application
import com.mdshahsamir.ovisharcinemahall.di.AppComponent
import com.mdshahsamir.ovisharcinemahall.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}