package com.mdshahsamir.ovisharcinemahall.ui.movies

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.databinding.MovieListItemBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie

class MovieListAdapter(private val glideRequestManager: RequestManager) :
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
            binding.releaseDateTextView.text = movie.release_date

            glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.poster_path)
                .placeholder(R.drawable.loading_animation)
                .into(binding.moviePosterImage)

            binding.imageView.apply {
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        if (movie.isAddedToWishlist) R.drawable.avd_anim_reverse else R.drawable.avd_anim,
                        null
                    )
                )

                drawableAnimation = drawable as AnimatedVectorDrawable
            }

            binding.imageView.setOnClickListener {
                movie.isAddedToWishlist = !movie.isAddedToWishlist
                drawableAnimation.start()
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
