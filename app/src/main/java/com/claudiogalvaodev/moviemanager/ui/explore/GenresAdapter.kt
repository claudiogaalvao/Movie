package com.claudiogalvaodev.moviemanager.ui.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ItemGenderListItemBinding
import com.claudiogalvaodev.moviemanager.model.Genre
import com.claudiogalvaodev.moviemanager.model.Movie

class GenresAdapter: ListAdapter<Genre, GenresAdapter.GenresViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GenresViewHolder(private val binding: ItemGenderListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.itemGenderListItemImageCover.setImageResource(R.drawable.all_cover_movies)
            binding.itemGenderListItemTitle.text = genre.name
        }

        companion object {
            fun create(parent: ViewGroup): GenresViewHolder {
                val binding = ItemGenderListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return GenresViewHolder(binding)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }
}