package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
            if (moment.imageUri == null) {
                binding.root.setOnClickListener {
                    favoriteMomentActionListener.onClickAddMoment()
                }

            } else {
               binding.image.setImageURI(moment.imageUri)
            }
        }
    }
}

object FavoriteMomentDiff: DiffUtil.ItemCallback<FavoriteMoment>() {
    override fun areItemsTheSame(oldItem: FavoriteMoment, newItem: FavoriteMoment): Boolean {
        return oldItem.imageUri?.path == newItem.imageUri?.path
    }

    override fun areContentsTheSame(oldItem: FavoriteMoment, newItem: FavoriteMoment): Boolean {
        return oldItem == newItem
    }
}
