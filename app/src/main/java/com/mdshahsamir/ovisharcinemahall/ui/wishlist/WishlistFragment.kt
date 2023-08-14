package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentWishlistBinding
import com.mdshahsamir.ovisharcinemahall.di.WishListRepoDependencyInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie

class WishlistFragment : BaseFragment<FragmentWishlistBinding>(), WishListItemActionListener {

    private val viewModel: WishListViewModel by viewModels {
        WishListViewModelFactory(WishListRepoDependencyInjector(requireContext()).getMovieListRepository())
    }

    private val adapter: WishListAdapter by lazy {
        WishListAdapter(Glide.with(requireContext()), this)
    }

    override fun getViewBinding(): FragmentWishlistBinding = FragmentWishlistBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = viewModel

    override fun setUpViews() {
        super.setUpViews()

        binding.wishListRecyclerView.adapter = adapter
        viewModel.loadWishList()
    }

    override fun observeData() {
        super.observeData()

        viewModel.wishlist.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun removeFromWishList(movie: Movie, position: Int) {
        viewModel.removeFromWishList(movie)
        adapter.notifyItemRemoved(position)
    }

    override fun onClickMovie(movieId: Int) {

    }
}
