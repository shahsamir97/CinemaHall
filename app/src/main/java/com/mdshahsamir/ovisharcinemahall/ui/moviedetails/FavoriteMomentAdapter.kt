package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mdshahsamir.ovisharcinemahall.databinding.FavoriteMomentItemBinding
import com.mdshahsamir.ovisharcinemahall.model.FavoriteMoment

class FavoriteMomentAdapter(private val favoriteMomentActionListener: FavoriteMomentActionListener) : ListAdapter<FavoriteMoment, FavoriteMomentAdapter.FavoriteMomentViewHolder>(FavoriteMomentDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMomentViewHolder {
        val binding = FavoriteMomentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMomentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMomentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteMomentViewHolder(private val binding: FavoriteMomentItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(moment: FavoriteMoment) {
            if (moment.imagePath.isEmpty()) {
                binding.root.setOnClickListener {
                    favoriteMomentActionListener.onClickAddMoment()
                }

                Glide.with(binding.root.context).load(moment.imagePath).into(binding.image)
            }
        }
    }
}

object FavoriteMomentDiff: DiffUtil.ItemCallback<FavoriteMoment>() {
    override fun areItemsTheSame(oldItem: FavoriteMoment, newItem: FavoriteMoment): Boolean {
        return oldItem.imagePath == newItem.imagePath
    }

    override fun areContentsTheSame(oldItem: FavoriteMoment, newItem: FavoriteMoment): Boolean {
        return oldItem == newItem
    }
}
