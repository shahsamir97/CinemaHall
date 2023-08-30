package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding
import com.mdshahsamir.ovisharcinemahall.di.DashboardRepositoryInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModel
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : BaseFragment<FragmentMovieListBinding>(),
    MovieListItemActionListener {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(Glide.with(requireContext()), this)
    }

    private val sharedViewModel: DashboardViewModel by activityViewModels {
        DashboardViewModelFactory(DashboardRepositoryInjector(requireContext()).getSharedRepository())
    }

    override fun getViewBinding(): FragmentMovieListBinding =
        FragmentMovieListBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = sharedViewModel

    override fun setUpViews() {
        setHasOptionsMenu(true)
        binding.movieRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.movieList.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh == LoadState.Loading
            if (it.refresh is LoadState.Error) {
                Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
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

    override fun onClickMovie(movieId: Int) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(action)
    }
}
