package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
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
import com.mdshahsamir.ovisharcinemahall.di.DashboardRepositoryInjector
import com.mdshahsamir.ovisharcinemahall.di.MovieDetailsRepoDependencyInjector
import com.mdshahsamir.ovisharcinemahall.model.FavoriteMoment
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetails
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModel
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModelFactory
import com.mdshahsamir.ovisharcinemahall.util.getDottedText
import com.mdshahsamir.ovisharcinemahall.util.runIfInternetAvailable
import kotlinx.coroutines.launch

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(),
    RecommendedMovieActionListener, FavoriteMomentActionListener {

    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var drawableAnimation: AnimatedVectorDrawable

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(
            args.movieId,
            args.isOnWishList,
            MovieDetailsRepoDependencyInjector.getMovieListRepository()
        )
    }

    private val adapter: RecommendedMoviesAdapter by lazy {
        RecommendedMoviesAdapter(Glide.with(requireContext()), this)
    }

    private val favoriteMomentAdapter: FavoriteMomentAdapter by lazy {
        FavoriteMomentAdapter(this)
    }

    private val sharedViewModel: DashboardViewModel by activityViewModels {
        DashboardViewModelFactory(DashboardRepositoryInjector(requireContext()).getSharedRepository())
    }

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    override fun getViewBinding(): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                //viewModel.addFavoriteMoment(FavoriteMoment(uri.path!!))
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.apply {
            recommendedMovieRecyclerView.adapter = adapter
            favoriteMomentRecyclerView.adapter = favoriteMomentAdapter
        }
    }

    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetailsFlow.collect { uiState ->
                    binding.dataLoadingProgressBar.isVisible =
                        uiState is MovieDetailsUiState.Loading
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

        viewModel.favoriteMoments.observe(viewLifecycleOwner) {
            favoriteMomentAdapter.submitList(it)
            favoriteMomentAdapter.notifyDataSetChanged()
        }
    }

    private fun showRecommendedMovies(movies: List<Movie>) {
        binding.apply {
            recommendedMoviesTitle.visibility = View.VISIBLE
            recommendedMovieRecyclerView.visibility = View.VISIBLE
        }

        adapter.submitList(movies)
    }

    private fun showMovieDetails(movieDetails: MovieDetails?) {
        binding.apply {
            dataLoadingProgressBar.visibility = View.GONE
            movieDetailsLayout.visibility = View.VISIBLE

            movieDetails?.let { movieDetails ->
                titleTextView.text =
                    movieDetails.originalTitle + " (${movieDetails.getReleaseYear()})"
                overviewTextView.text = movieDetails.overview
                popularityTextView.text = (movieDetails.voteAverage * 10).toInt().toString() + "%"
                popularityProgressBar.progress = (movieDetails.voteAverage * 10).toInt()
                genreTextView.text = getDottedText(movieDetails.genres.map { it.name })
                adultContentIcon.isVisible = movieDetails.adult
                binding.wishListStateText.text =
                    if (viewModel.isAddedToWishlist) getString(R.string.in_your_wishlist) else getString(
                        R.string.add_to_wishlist
                    )
                switchAnimDrawable(viewModel.isAddedToWishlist)

                addToWishListImageView.setOnClickListener {
                    if (!viewModel.isAddedToWishlist) {
                        val movie = Movie(
                            movieDetails.adult,
                            movieDetails.id,
                            movieDetails.originalTitle,
                            movieDetails.overview,
                            movieDetails.posterPath,
                            movieDetails.releaseDate,
                            movieDetails.originalTitle,
                            movieDetails.voteAverage,
                            true
                        )
                        sharedViewModel.addMovieToWishList(movie)
                        runAddToWishlistIconAnimation(viewModel.isAddedToWishlist)
                        binding.wishListStateText.text = getString(R.string.in_your_wishlist)
                    }
                }

                Glide.with(requireContext())
                    .load(BuildConfig.IMAGE_BASE_URL + movieDetails.posterPath)
                    .placeholder(R.drawable.loading_animation).into(moviePosterImageView)

                Glide.with(requireContext())
                    .load(BuildConfig.IMAGE_BASE_URL + movieDetails.backdropPath)
                    .placeholder(R.drawable.loading_animation).into(backgroundPosterImageView)
            }
        }
    }

    override fun onClickMovie(movie: Movie) {
        runIfInternetAvailable(requireContext()) {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                movie.id, movie.isAddedToWishlist
            )
            findNavController().navigate(action)
        }
    }

    private fun runAddToWishlistIconAnimation(isAddedToWishList: Boolean) {
        viewModel.isAddedToWishlist = !viewModel.isAddedToWishlist
        switchAnimDrawable(isAddedToWishList)
        drawableAnimation.start()
    }

    private fun switchAnimDrawable(isAddedToWishList: Boolean) {
        binding.addToWishListImageView.apply {
            setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    if (isAddedToWishList) R.drawable.heart_to_add_animated_vector else R.drawable.add_to_heart_animated_vector,
                    null
                )
            )

            drawableAnimation = drawable as AnimatedVectorDrawable
        }
    }

    override fun onClickAddMoment() {
        pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}
