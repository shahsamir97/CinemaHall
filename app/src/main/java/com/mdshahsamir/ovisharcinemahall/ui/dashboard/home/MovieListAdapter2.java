package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.content.res.ResourcesCompat;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.mdshahsamir.ovisharcinemahall.BuildConfig;
import com.mdshahsamir.ovisharcinemahall.R;
import com.mdshahsamir.ovisharcinemahall.databinding.MovieListItemBinding;
import com.mdshahsamir.ovisharcinemahall.model.Movie;
import com.mdshahsamir.ovisharcinemahall.util.ExtensionsKt;
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil;
import com.mdshahsamir.ovisharcinemahall.util.UtilKt;

public class MovieListAdapter2 extends PagingDataAdapter<Movie, MovieListAdapter2.MovieViewHolder> {
    private final RequestManager glideRequestManager;
    private final MovieListItemActionListener itemActionListener;

    public MovieListAdapter2(RequestManager glideRequestManager, MovieListItemActionListener itemActionListener) {
        super(MovieDiffUtil.INSTANCE);
        this.glideRequestManager = glideRequestManager;
        this.itemActionListener = itemActionListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding binding = MovieListItemBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieListItemBinding binding;
        private AnimatedVectorDrawable drawableAnimation;

        public MovieViewHolder(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            Context context = binding.getRoot().getContext();
            binding.titleTextView.setText(movie.getTitle());
            binding.releaseDateTextView.setText(ExtensionsKt.toDisplayableDateFormat(movie.getReleaseDate(),
                    UtilKt.API_DATE_FORMAT));

            switchAnimDrawable(movie);

            glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.loading_animation).into(binding.moviePosterImage);

            binding.getRoot().setOnClickListener(view -> itemActionListener.onClickMovie(movie));

            binding.imageView.setOnClickListener(view -> {
                try {
                    if (movie.isAddedToWishlist()) {
                        itemActionListener.onClickRemoveFromWishlist(movie);
                    } else {
                        itemActionListener.onClickAddToWishlist(movie);
                    }

                    runAddToWishlistIconAnimation(movie);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.failed_to_add_or_remove_from_wishlist), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void runAddToWishlistIconAnimation(Movie movie) {
            switchAnimDrawable(movie);
            movie.setAddedToWishlist(!movie.isAddedToWishlist());
            drawableAnimation.start();
        }

        private void switchAnimDrawable(Movie movie) {
            binding.imageView.setImageDrawable(ResourcesCompat.getDrawable(binding.imageView.getResources(),
                    movie.isAddedToWishlist() ? R.drawable.heart_to_add_animated_vector : R.drawable.add_to_heart_animated_vector,
                    null));

            drawableAnimation = (AnimatedVectorDrawable) binding.imageView.getDrawable();
        }
    }
}
