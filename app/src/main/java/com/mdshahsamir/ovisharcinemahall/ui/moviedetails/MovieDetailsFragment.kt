package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieDetailsBinding
import com.mdshahsamir.ovisharcinemahall.di.MovieDetailsRepoDependencyInjector
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import kotlinx.coroutines.launch

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(MovieDetailsRepoDependencyInjector.getMovieListRepository())
    }

    val args: MovieDetailsFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun setUpViews() {
        super.setUpViews()

        viewModel.loadMovieDetails(args.movieId)
    }

    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is MovieDetailsUiState.Loading -> {
                            showLoader()
                        }

                        is MovieDetailsUiState.Success -> {
                            showMovieDetails(uiState.movieDetails)
                        }

                        is MovieDetailsUiState.Error -> {
                            showErrorMessage()
                        }
                    }
                }
            }
        }
    }

    private fun showMovieDetails(movieDetails: MovieDetailsResponse?) {
        binding.dataLoadingProgressBar.visibility = View.GONE
        binding.errorMessageTextView.visibility = View.GONE
        binding.movieDetailsLayout.visibility = View.VISIBLE

        movieDetails?.let { movie ->
            binding.titleTextView.text = movie.title
            binding.overviewTextView.text = movie.overview
            binding.popularityTextView.text = (movie.voteAverage * 10).toInt().toString() + "%"
            binding.popularityProgressBar.progress = (movie.voteAverage * 10).toInt()

            Glide.with(requireContext()).load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                .placeholder(R.drawable.loading_animation)
                .into(binding.moviePosterImageView)

            Glide.with(requireContext()).load(BuildConfig.IMAGE_BASE_URL + movie.backdropPath)
                .placeholder(R.drawable.loading_animation)
                .into(binding.backgroundPosterImageView)
        }
    }

    private fun showLoader() {
        binding.errorMessageTextView.visibility = View.GONE
        binding.movieDetailsLayout.visibility = View.GONE
        binding.dataLoadingProgressBar.visibility = View.VISIBLE
    }

    private fun showErrorMessage() {
        binding.errorMessageTextView.visibility = View.VISIBLE
        binding.dataLoadingProgressBar.visibility = View.GONE
        binding.movieDetailsLayout.visibility = View.GONE
    }
}
