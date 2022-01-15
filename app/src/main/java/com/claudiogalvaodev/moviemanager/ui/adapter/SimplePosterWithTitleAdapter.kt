package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.ItemSimplePosterWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter.*
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils.dateFromAmericanFormatToDateWithMonthName
import com.squareup.picasso.Picasso

class SimplePosterWithTitleAdapter: ListAdapter<Movie, SimplePosterWithTitleViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((movie: Movie) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimplePosterWithTitleViewHolder {
        return SimplePosterWithTitleViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: SimplePosterWithTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
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

    class SimplePosterWithTitleViewHolder(
        private val binding: ItemSimplePosterWithTitleBinding,
        private val clickListener: ((movie: Movie) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getPoster()).into(moviePosterWithTitleImage)
                moviePosterWithTitleTitle.text = movie.title
                moviePosterWithTitleRelease.text = dateFromAmericanFormatToDateWithMonthName(movie.release_date)

                binding.moviePosterWithTitleImage.setOnClickListener {
                    clickListener?.invoke(movie)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((movie: Movie) -> Unit)?): SimplePosterWithTitleViewHolder {
                val binding = ItemSimplePosterWithTitleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return SimplePosterWithTitleViewHolder(binding, clickListener)
            }
        }
    }
}