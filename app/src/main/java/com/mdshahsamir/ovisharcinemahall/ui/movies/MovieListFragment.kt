package com.mdshahsamir.ovisharcinemahall.ui.movies

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentMovieListBinding
import com.mdshahsamir.ovisharcinemahall.di.SharedRepositoryInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.shared.SharedViewModelFactory
import com.mdshahsamir.ovisharcinemahall.ui.shared.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : BaseFragment<FragmentMovieListBinding>(),
    MovieListItemActionListener {

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(Glide.with(requireContext()), this)
    }

    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(SharedRepositoryInjector(requireContext()).getSharedRepository())
    }

    override fun getViewBinding(): FragmentMovieListBinding =
        FragmentMovieListBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = sharedViewModel

    override fun setUpViews() {
        binding.movieRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.movieList.collectLatest {
                adapter.submitData(it)
            }
        }

        sharedViewModel.wishList.observe(viewLifecycleOwner) {

        }
    }

    override fun onClickAddToWishlist(movie: Movie) {
        sharedViewModel.addMovieToWishList(movie)
    }

    override fun onClickRemoveFromWishlist(movie: Movie) {
        sharedViewModel.removeFromWishList(movie)
    }

    override fun onClickMovie(movieId: Int) {
        Log.i("Clicked On Movie ::", movieId.toString())
    }
}
