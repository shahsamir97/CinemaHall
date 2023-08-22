package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding
import com.mdshahsamir.ovisharcinemahall.di.MovieListRepoDependencyInjector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(Glide.with(requireContext()))
    }

    private val viewModel: MovieListViewModel by viewModels {
        MovieListViewModelFactory(MovieListRepoDependencyInjector.getMovieListRepository())
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

        adapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
        }
    }
}