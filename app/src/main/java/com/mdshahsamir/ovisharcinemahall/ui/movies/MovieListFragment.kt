package com.mdshahsamir.ovisharcinemahall.ui.movies

import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding

class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    override fun getViewModel(): BaseViewModel? = null

    override fun getViewBinding(): FragmentMovieListBinding = FragmentMovieListBinding.inflate(layoutInflater)

    override fun setUpViews() {}
}
