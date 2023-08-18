package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.databinding.MovieListItemBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie

class MovieListAdapter(
    private val glideRequestManager: RequestManager,
    private val itemActionListener: MovieListItemActionListener
) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var drawableAnimation: AnimatedVectorDrawable

        fun bind(movie: Movie) {
            binding.titleTextView.text = movie.title
            binding.releaseDateTextView.text = movie.releaseDate
            switchAnimDrawable(movie)

            glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                .placeholder(R.drawable.loading_animation)
                .into(binding.moviePosterImage)

            binding.root.setOnClickListener {
                itemActionListener.onClickMovie(movie.id)
            }

            binding.imageView.setOnClickListener {
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
                        binding.root.context,
                        binding.root.context.getString(R.string.failed_to_add_or_remove_from_wishlist),
                        Toast.LENGTH_SHORT
                    ).show()
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

    object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}