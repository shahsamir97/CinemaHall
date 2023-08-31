package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieDetailsBinding
import com.mdshahsamir.ovisharcinemahall.di.MovieDetailsRepoDependencyInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetails
import com.mdshahsamir.ovisharcinemahall.util.getDottedText
import com.mdshahsamir.ovisharcinemahall.util.runIfInternetAvailable
import kotlinx.coroutines.launch

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(), RecommendedMovieActionListener {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(args.movieId, MovieDetailsRepoDependencyInjector.getMovieListRepository())
    }

    private val adapter: RecommendedMoviesAdapter by lazy {
        RecommendedMoviesAdapter(Glide.with(requireContext()), this)
    }

    override fun getViewBinding(): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetailsFlow.collect { uiState ->
                    binding.dataLoadingProgressBar.isVisible = uiState is MovieDetailsUiState.Loading
                    binding.noInternetDialog.root.isVisible = uiState is MovieDetailsUiState.Error
                    binding.movieDetailsLayout.isVisible = uiState is MovieDetailsUiState.Success

                    when (uiState) {
                        is MovieDetailsUiState.Success -> {
                            showMovieDetails(uiState.movieDetails)
                            showRecommendedMovies(uiState.recommendedMovies)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showRecommendedMovies(movies: List<Movie>) {
        binding.apply {
            recommendedMoviesTitle.visibility = View.VISIBLE
            recommendedMovieRecyclerView.visibility = View.VISIBLE
            recommendedMovieRecyclerView.adapter = adapter
        }

        adapter.submitList(movies)
    }

    private fun showMovieDetails(movieDetails: MovieDetails?) {
        binding.apply {
            dataLoadingProgressBar.visibility = View.GONE
            movieDetailsLayout.visibility = View.VISIBLE

            movieDetails?.let { movie ->
                titleTextView.text = movie.originalTitle + " (${movie.getReleaseYear()})"
                overviewTextView.text = movie.overview
                popularityTextView.text = (movie.voteAverage * 10).toInt().toString() + "%"
                popularityProgressBar.progress = (movie.voteAverage * 10).toInt()
                genreTextView.text = getDottedText(movie.genres.map { it.name })

                Glide.with(requireContext()).load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.loading_animation)
                    .into(moviePosterImageView)

                Glide.with(requireContext()).load(BuildConfig.IMAGE_BASE_URL + movie.backdropPath)
                    .placeholder(R.drawable.loading_animation)
                    .into(backgroundPosterImageView)
            }
        }
    }

    override fun onClickMovie(movieId: Int) {
        runIfInternetAvailable(requireContext()) {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(movieId)
            findNavController().navigate(action)
        }
    }
}
