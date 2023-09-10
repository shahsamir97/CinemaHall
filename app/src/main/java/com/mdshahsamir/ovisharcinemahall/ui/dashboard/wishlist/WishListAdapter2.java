package com.mdshahsamir.ovisharcinemahall.ui.dashboard.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.mdshahsamir.ovisharcinemahall.BuildConfig;
import com.mdshahsamir.ovisharcinemahall.R;
import com.mdshahsamir.ovisharcinemahall.databinding.WishlistItemBinding;
import com.mdshahsamir.ovisharcinemahall.model.Movie;
import com.mdshahsamir.ovisharcinemahall.util.MovieDiffUtil;

public class WishListAdapter2
        extends ListAdapter<Movie, WishListAdapter2.WishListViewHolder2> {

    private final RequestManager glideRequestManager;
    private final WishListItemActionListener itemActionListener;

    public WishListAdapter2(RequestManager glideRequestManager, WishListItemActionListener wishListItemActionListener) {
        super(MovieDiffUtil.INSTANCE);
        this.glideRequestManager = glideRequestManager;
        this.itemActionListener = wishListItemActionListener;
    }

    @Override
    public WishListViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WishlistItemBinding binding = WishlistItemBinding.inflate(inflater, parent, false);

        return new WishListViewHolder2(binding);
    }

    @Override
    public void onBindViewHolder(WishListViewHolder2 holder, int position) {
        Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    class WishListViewHolder2 extends RecyclerView.ViewHolder {

        private final WishlistItemBinding binding;

        public WishListViewHolder2(WishlistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            binding.titleTextView.setText(movie.getTitle());
            binding.releaseDateTextView.setText(movie.getReleaseDate());

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
                    itemActionListener.removeFromWishList(movie);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(
                            view.getContext(),
                            binding.getRoot().getContext().getString(R.string.failed_to_add_or_remove_from_wishlist),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }
}
