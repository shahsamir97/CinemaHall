package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieDetailsBinding
import com.mdshahsamir.ovisharcinemahall.di.MovieDetailsRepoDependencyInjector

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(MovieDetailsRepoDependencyInjector.getMovieListRepository())
    }

    val args: MovieDetailsFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun setUpViews() {
        super.setUpViews()

        viewModel.loadMovieDetails(args.movieId)
    }
}
