package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemSimplePosterBinding
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.ItemSimplePosterWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterAdapter.SimplePosterViewHolder
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils.dateFromAmericanFormatToDateWithMonthName
import com.squareup.picasso.Picasso

class SimplePosterAdapter: ListAdapter<Movie, SimplePosterViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((movie: Movie) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimplePosterViewHolder {
        return SimplePosterViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: SimplePosterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SimplePosterViewHolder(
        private val binding: ItemSimplePosterBinding,
        private val clickListener: ((movie: Movie) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getPoster()).into(itemSimplePosterImage)

                binding.root.setOnClickListener {
                    clickListener?.invoke(movie)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((movie: Movie) -> Unit)?): SimplePosterViewHolder {
                val binding = ItemSimplePosterBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return SimplePosterViewHolder(binding, clickListener)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}