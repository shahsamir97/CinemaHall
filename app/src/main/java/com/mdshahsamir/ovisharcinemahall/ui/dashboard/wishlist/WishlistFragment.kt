package com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentWishlistBinding
import com.mdshahsamir.ovisharcinemahall.di.DashboardRepositoryInjector
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModel
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModelFactory
import com.mdshahsamir.ovisharcinemahall.util.runIfInternetAvailable

class WishlistFragment : BaseFragment<FragmentWishlistBinding>(), WishListItemActionListener {

    private val sharedViewModel: DashboardViewModel by activityViewModels {
        DashboardViewModelFactory(DashboardRepositoryInjector(requireContext()).getSharedRepository())
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
            binding.emptyWishListText.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(it)
        }
    }

    override fun removeFromWishList(movie: Movie) {
        sharedViewModel.removeFromWishList(movie)
    }

    override fun onClickMovie(movie: Movie) {
        runIfInternetAvailable(requireContext(), "You're in offline mode") {
            val action = WishlistFragmentDirections.actionWishlistFragmentToMovieDetailsFragment(movie.id, movie.isAddedToWishlist)
            findNavController().navigate(action)
        }
    }
}
