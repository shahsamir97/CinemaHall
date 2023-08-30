package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.mdshahsamir.ovisharcinemahall.BuildConfig
import com.mdshahsamir.ovisharcinemahall.R
import com.mdshahsamir.ovisharcinemahall.databinding.RecommendedMoviesListItemBinding
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.util.API_DATE_FORMAT
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil
import com.mdshahsamir.ovisharcinemahall.util.toDisplayableDateFormat

class RecommendedMoviesAdapter(
    private val glideRequestManager: RequestManager,
    private val actionListener: RecommendedMovieActionListener
) : ListAdapter<Movie, RecommendedMoviesAdapter.RecommendedMovieViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMovieViewHolder {
        val binding = RecommendedMoviesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecommendedMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecommendedMovieViewHolder(private val binding: RecommendedMoviesListItemBinding) :
        ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.titleTextView.text = movie.title
            binding.releaseDateTextView.text = movie.releaseDate.toDisplayableDateFormat(API_DATE_FORMAT)

            glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                .placeholder(R.drawable.loading_animation)
                .into(binding.moviePosterImage)

            binding.root.setOnClickListener {
                actionListener.onClickMovie(movie.id)
            }
        }
    }
}
