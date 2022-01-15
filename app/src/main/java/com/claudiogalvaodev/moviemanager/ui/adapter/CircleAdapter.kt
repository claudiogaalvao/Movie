package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemSmallImageCircleBinding
import com.claudiogalvaodev.moviemanager.model.Company
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Provider
import com.squareup.picasso.Picasso

class CircleAdapter: ListAdapter<Any, CircleAdapter.CircleAdapterViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleAdapterViewHolder {
        return CircleAdapterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CircleAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CircleAdapterViewHolder(private val binding: ItemSmallImageCircleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            with(binding) {
                when(obj) {
                    is Employe -> {
                        Picasso.with(root.context).load(obj.getProfileImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                    is Company -> {
                        itemImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                        Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                    is Provider -> {
                        itemImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                        Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                }

            }
        }

        companion object {
            fun create(parent: ViewGroup): CircleAdapterViewHolder {
                val binding = ItemSmallImageCircleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return CircleAdapterViewHolder(binding)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

        }
    }
}