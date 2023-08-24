package com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.databinding.WishlistItemBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil

class WishListAdapter(
    private val glideRequestManager: RequestManager,
    private val itemActionListener: WishListItemActionListener
): ListAdapter<Movie, WishListAdapter.WishListViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val binding = WishlistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return WishListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class WishListViewHolder(private val binding: WishlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                titleTextView.text = movie.title
                releaseDateTextView.text = movie.releaseDate

                glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.loading_animation)
                    .into(moviePosterImage)

                root.setOnClickListener {
                    itemActionListener.onClickMovie(movie.id)
                }

                imageView.setOnClickListener {
                    try {
                        itemActionListener.removeFromWishList(movie)
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            root.context,
                            binding.root.context.getString(R.string.failed_to_add_or_remove_from_wishlist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
