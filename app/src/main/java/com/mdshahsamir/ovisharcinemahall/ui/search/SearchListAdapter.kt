package com.mdshahsamir.ovisharcinemahall.ui.search

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.databinding.MovieListItemBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListItemActionListener
import com.mdshahsamir.ovisharcinemahall.util.API_DATE_FORMAT
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil
import com.mdshahsamir.ovisharcinemahall.util.toDisplayableDateFormat

class SearchListAdapter(
    private val glideRequestManager: RequestManager,
    private val itemActionListener: MovieListItemActionListener
): ListAdapter<Movie, SearchListAdapter.SearchListViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SearchListViewHolder(private val binding: MovieListItemBinding) : ViewHolder(binding.root) {

        private lateinit var drawableAnimation: AnimatedVectorDrawable

        fun bind(movie: Movie) {
            binding.apply {
                titleTextView.text = movie.title
                releaseDateTextView.text = movie.releaseDate.toDisplayableDateFormat(API_DATE_FORMAT)
                switchAnimDrawable(movie)

                glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.loading_animation)
                    .into(moviePosterImage)

                root.setOnClickListener {
                    itemActionListener.onClickMovie(movie)
                }

                imageView.setOnClickListener {
                    try {
                        if (movie.isAddedToWishlist) {
                            itemActionListener.onClickRemoveFromWishlist(movie)
                        } else {
                            itemActionListener.onClickAddToWishlist(movie)
                        }

                        runAddToWishlistIconAnimation(movie)
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            root.context,
                            root.context.getString(R.string.failed_to_add_or_remove_from_wishlist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        private fun runAddToWishlistIconAnimation(movie: Movie) {
            switchAnimDrawable(movie)
            movie.isAddedToWishlist = !movie.isAddedToWishlist
            drawableAnimation.start()
        }

        private fun switchAnimDrawable(movie: Movie) {
            binding.imageView.apply {
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        if (movie.isAddedToWishlist) R.drawable.heart_to_add_animated_vector else R.drawable.add_to_heart_animated_vector,
                        null
                    )
                )

                drawableAnimation = drawable as AnimatedVectorDrawable
            }
        }
    }
}
