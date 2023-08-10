package com.mdshahsamir.ovisharcinemahall.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {

    protected lateinit var binding: VBinding
    protected var mViewModel: BaseViewModel? = null

    protected abstract fun getViewModel(): BaseViewModel?
    protected abstract fun getViewBinding(): VBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}

    open fun observeData() {}

    private fun init() {
        binding = getViewBinding()
        getViewModel()?.let {
            mViewModel = ViewModelProvider(this)[it::class.java]
        }
    }
}
