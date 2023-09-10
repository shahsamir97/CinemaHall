package com.mdshahsamir.ovisharcinemahall.ui.moviedetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.mdshahsamir.ovisharcinemahall.BuildConfig;
import com.mdshahsamir.ovisharcinemahall.R;
import com.mdshahsamir.ovisharcinemahall.databinding.RecommendedMoviesListItemBinding;
import com.mdshahsamir.ovisharcinemahall.model.Movie;
import com.mdshahsamir.ovisharcinemahall.util.ExtensionsKt;
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil;
import com.mdshahsamir.ovisharcinemahall.util.UtilKt;

public class RecommendedMoviesAdapter2 extends ListAdapter<Movie, RecommendedMoviesAdapter2.RecommendedMovieViewHolder2> {

    private final RequestManager glideRequestManager;
    private final RecommendedMovieActionListener actionListener;

    public RecommendedMoviesAdapter2(RequestManager glideRequestManager,
                                     RecommendedMovieActionListener actionListener) {
        super(MovieDiffUtil.INSTANCE);
        this.glideRequestManager = glideRequestManager;
        this.actionListener = actionListener;
    }

    @Override
    public RecommendedMovieViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecommendedMoviesListItemBinding binding = RecommendedMoviesListItemBinding.inflate(inflater, parent, false);

        return new RecommendedMovieViewHolder2(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedMovieViewHolder2 holder, int position) {
        Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    class RecommendedMovieViewHolder2 extends RecyclerView.ViewHolder {

        private final RecommendedMoviesListItemBinding binding;

        public RecommendedMovieViewHolder2(RecommendedMoviesListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            binding.titleTextView.setText(movie.getTitle());
            binding.releaseDateTextView.setText(ExtensionsKt.toDisplayableDateFormat(movie.getReleaseDate(),
                    UtilKt.API_DATE_FORMAT));

            glideRequestManager.load(BuildConfig.IMAGE_BASE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.loading_animation)
                    .into(binding.moviePosterImage);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionListener.onClickMovie(movie);
                }
            });
        }
    }
}
