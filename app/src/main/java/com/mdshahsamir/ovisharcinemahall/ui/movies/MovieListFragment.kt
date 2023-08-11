package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding
import com.mdshahsamir.ovisharcinemahall.di.MovieListRepoDependencyInjector

class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(Glide.with(requireContext()))
    }

    private val viewModel : MovieListViewModel by viewModels {
        MovieListViewModelFactory(MovieListRepoDependencyInjector.getMovieListRepository())
    }

    override fun getViewBinding(): FragmentMovieListBinding = FragmentMovieListBinding.inflate(layoutInflater)

    override fun setUpViews() {
        binding.movieRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        viewModel.loadData()
        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
