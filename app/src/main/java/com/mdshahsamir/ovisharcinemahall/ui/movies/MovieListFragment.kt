package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding
import com.mdshahsamir.ovisharcinemahall.di.MovieListRepoDependencyInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : BaseFragment<FragmentMovieListBinding>(),
    MovieListItemActionListener {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(Glide.with(requireContext()), this)
    }

    private val viewModel: MovieListViewModel by viewModels {
        MovieListViewModelFactory(
            MovieListRepoDependencyInjector(requireContext().applicationContext)
                .getMovieListRepository()
        )
    }

    override fun getViewBinding(): FragmentMovieListBinding =
        FragmentMovieListBinding.inflate(layoutInflater)

    override fun setUpViews() {
        binding.movieRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieList.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onClickMovie(movie: Movie) {
        viewModel.addMovieToWishList(movie)
    }
}
