package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.navigation.fragment.findNavController
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = getString(R.string.search_movies)

        val textView: TextView =  searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        textView.apply {
            setTextColor(Color.WHITE)
            setHintTextColor(Color.WHITE)
        }

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {  }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {  }

                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.search ->
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
