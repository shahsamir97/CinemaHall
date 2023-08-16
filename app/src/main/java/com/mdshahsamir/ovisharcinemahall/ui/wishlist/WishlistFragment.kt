package com.mdshahsamir.ovisharcinemahall.ui.wishlist

import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentWishlistBinding
import com.mdshahsamir.ovisharcinemahall.di.SharedRepositoryInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.shared.SharedViewModel
import com.mdshahsamir.ovisharcinemahall.ui.shared.SharedViewModelFactory

class WishlistFragment : BaseFragment<FragmentWishlistBinding>(), WishListItemActionListener {

    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(SharedRepositoryInjector(requireContext()).getSharedRepository())
    }

    private val adapter: WishListAdapter by lazy {
        WishListAdapter(Glide.with(requireContext()), this)
    }

    override fun getViewBinding(): FragmentWishlistBinding = FragmentWishlistBinding.inflate(layoutInflater)

    override fun getViewModel(): BaseViewModel = sharedViewModel

    override fun setUpViews() {
        super.setUpViews()

        binding.wishListRecyclerView.adapter = adapter
    }

    override fun observeData() {
        super.observeData()

        sharedViewModel.wishList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun removeFromWishList(movie: Movie) {
        sharedViewModel.removeFromWishList(movie)
    }

    override fun onClickMovie(movieId: Int) {}
}
