package com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.MyApplication
import com.mdshahsamir.ovisharcinemahall.base.BaseFragment
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.databinding.FragmentWishlistBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardViewModel
import javax.inject.Inject

class WishlistFragment : BaseFragment<FragmentWishlistBinding>(), WishListItemActionListener {

    @Inject
    lateinit var sharedViewModel: DashboardViewModel

    private val adapter: WishListAdapter by lazy {
        WishListAdapter(Glide.with(requireContext()), this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
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

    override fun onClickMovie(movieId: Int) {}
}
