package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.view.View
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
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import com.mdshahsamir.ovisharcinemahall.util.getDottedText
import com.mdshahsamir.ovisharcinemahall.util.getYearFromApiDate
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
                    when (uiState) {
                        is MovieDetailsUiState.Loading -> {
                            showLoader()
                        }

                        is MovieDetailsUiState.Success -> {
                            showMovieDetails(uiState.movieDetails)
                            showRecommendedMovies(uiState.recommendedMovies)
                        }

                        is MovieDetailsUiState.Error -> {
                            showErrorMessage()
                        }
                    }
                }
            }
        }
    }

    private fun showRecommendedMovies(movies: List<Movie>) {
        binding.recommendedMoviesTitle.visibility = View.VISIBLE
        binding.recommendedMovieRecyclerView.visibility = View.VISIBLE
        binding.recommendedMovieRecyclerView.adapter = adapter
        adapter.submitList(movies)
    }

    private fun showMovieDetails(movieDetails: MovieDetailsResponse?) {
        binding.dataLoadingProgressBar.visibility = View.GONE
        binding.errorMessageTextView.visibility = View.GONE
        binding.movieDetailsLayout.visibility = View.VISIBLE

        movieDetails?.let { movie ->
            binding.titleTextView.text = movie.title + "(${getYearFromApiDate(movie.releaseDate)})"
            binding.overviewTextView.text = movie.overview
            binding.popularityTextView.text = (movie.voteAverage * 10).toInt().toString() + "%"
            binding.popularityProgressBar.progress = (movie.voteAverage * 10).toInt()
            binding.genreTextView.text = getDottedText(movie.genres.map { it.name })

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

    override fun onClickMovie(movieId: Int) {
        val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(movieId)
        findNavController().navigate(action)
    }
}
