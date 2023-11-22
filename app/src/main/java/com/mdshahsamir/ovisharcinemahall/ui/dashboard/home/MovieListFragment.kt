package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModel
import com.mdshahsamir.ovisharcinemahall.util.runIfInternetAvailable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragment : BaseFragment<FragmentMovieListBinding>(),
    MovieListItemActionListener {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(Glide.with(requireContext()), this)
    }

    @Inject lateinit var sharedViewModel: DashboardViewModel

    override fun getViewBinding(): FragmentMovieListBinding =
        FragmentMovieListBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = sharedViewModel

    override fun setUpViews() {
        setHasOptionsMenu(true)

        binding.movieRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                sharedViewModel.movieList.collectLatest { pagingData ->
                    adapter.submitData(
                    pagingData.map { movie ->
                        val isAddedToWishlist = sharedViewModel.wishList.value?.any { it.id == movie.id } ?: false
                        val modifiedMovie = movie.copy(isAddedToWishlist = isAddedToWishlist)
                        modifiedMovie
                    })
                }
            }
        }

        adapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
            binding.noInternetImageView.root.isVisible = it.refresh is LoadState.Error
        }

        sharedViewModel.wishList.observe(viewLifecycleOwner) {}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                sharedViewModel.allMovies = adapter.snapshot().items.sortedBy { it.title }
                sharedViewModel.filteredMovies.value = sharedViewModel.allMovies
                findNavController().navigate(R.id.action_movieListFragment_to_searchFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClickAddToWishlist(movie: Movie) {
        sharedViewModel.addMovieToWishList(movie)
    }

    override fun onClickRemoveFromWishlist(movie: Movie) {
        sharedViewModel.removeFromWishList(movie)
    }

    override fun onClickMovie(movie: Movie) {
       runIfInternetAvailable(requireContext()) {
           val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie.id, movie.isAddedToWishlist)
           findNavController().navigate(action)
       }
    }
}
