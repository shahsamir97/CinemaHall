package com.mdshahsamir.ovisharcinemahall.ui.search;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.mdshahsamir.ovisharcinemahall.BuildConfig;
import com.mdshahsamir.ovisharcinemahall.R;
import com.mdshahsamir.ovisharcinemahall.databinding.MovieListItemBinding;
import com.mdshahsamir.ovisharcinemahall.model.Movie;
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListItemActionListener;
import com.mdshahsamir.ovisharcinemahall.util.ExtensionsKt;
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil;
import com.mdshahsamir.ovisharcinemahall.util.UtilKt;

public class SearchListAdapter2 extends ListAdapter<Movie, SearchListAdapter2.SearchListViewHolder2> {

    private final RequestManager glideRequestManager;
    private final MovieListItemActionListener itemActionListener;

    public SearchListAdapter2(RequestManager glideRequestManager,
                              MovieListItemActionListener itemActionListener) {
        super(MovieDiffUtil.INSTANCE);
        this.glideRequestManager = glideRequestManager;
        this.itemActionListener = itemActionListener;
    }

    @Override
    public SearchListViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding binding = MovieListItemBinding.inflate(inflater, parent, false);

        return new SearchListViewHolder2(binding);
    }

    @Override
    public void onBindViewHolder(SearchListViewHolder2 holder, int position) {
        Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    class SearchListViewHolder2 extends RecyclerView.ViewHolder {

        private final MovieListItemBinding binding;
        private AnimatedVectorDrawable drawableAnimation;


        public SearchListViewHolder2(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            binding.titleTextView.setText(movie.getTitle());
            binding.releaseDateTextView.setText(ExtensionsKt.toDisplayableDateFormat(movie.getReleaseDate(),
                    UtilKt.API_DATE_FORMAT));
            switchAnimDrawable(movie);

            glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.loading_animation)
                    .into(binding.moviePosterImage);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemActionListener.onClickMovie(movie);
                }
            });

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
                    Toast.makeText(
                            view.getContext(),
                            view.getContext().getString(R.string.failed_to_add_or_remove_from_wishlist),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }

        public void runAddToWishlistIconAnimation(Movie movie) {
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
