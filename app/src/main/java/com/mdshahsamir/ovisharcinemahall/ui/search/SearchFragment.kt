package com.mdshahsamir.ovisharcinemahall.ui.search

import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentSearchBinding
import com.mdshahsamir.ovisharcinemahall.di.DashboardRepositoryInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModel
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModelFactory
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListItemActionListener
import com.mdshahsamir.ovisharcinemahall.util.runIfInternetAvailable

class SearchFragment : BaseFragment<FragmentSearchBinding>(), MovieListItemActionListener {

    private val sharedViewModel: DashboardViewModel by activityViewModels {
        DashboardViewModelFactory(DashboardRepositoryInjector(requireContext()).getSharedRepository())
    }

    private val adapter: SearchListAdapter by lazy {
        SearchListAdapter(Glide.with(requireContext()), this)
    }

    override fun setUpViews() {
        super.setUpViews()
        setHasOptionsMenu(true)

        binding.movieRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        sharedViewModel.filteredMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun getViewBinding(): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = sharedViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        setSearchViewConfig(searchItem, searchView)

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { sharedViewModel.filteredMoviesByTitle(it) }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedViewModel.filteredMoviesByTitle(it) }

                return true
            }
        })
    }

    private fun setSearchViewConfig(
        searchItem: MenuItem,
        searchView: SearchView
    ) {
        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.search_movies)

        searchItem.setOnActionExpandListener(object : OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                findNavController().navigateUp()
                return true
            }
        })

        val textView: TextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        textView.apply {
            setTextColor(Color.WHITE)
            setHintTextColor(Color.WHITE)
        }
    }

    override fun onClickAddToWishlist(movie: Movie) {
        sharedViewModel.addMovieToWishList(movie)
    }

    override fun onClickRemoveFromWishlist(movie: Movie) {
        sharedViewModel.removeFromWishList(movie)
    }

    override fun onClickMovie(movie: Movie) {
        runIfInternetAvailable(requireContext()) {
            val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(movie.id, movie.isAddedToWishlist)
            findNavController().navigate(action)
        }
    }
}
