package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.ui.model.SpecialItem
import com.claudiogalvaodev.moviemanager.databinding.ItemSimplePosterWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter.SimplePosterWithTitleViewHolder
import com.claudiogalvaodev.moviemanager.utils.enums.ItemType
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils.dateFromAmericanFormatToDateWithMonthName
import com.squareup.picasso.Picasso

class SimplePosterWithTitleAdapter: ListAdapter<Any, SimplePosterWithTitleViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((itemId: Int, type: ItemType, leastOneMovieId: Int, releaseDate: String) -> Unit)? = null
    var oscarCategory: OscarCategory? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimplePosterWithTitleViewHolder {
        return SimplePosterWithTitleViewHolder.create(parent, onItemClick, oscarCategory)
    }

    override fun onBindViewHolder(holder: SimplePosterWithTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SimplePosterWithTitleViewHolder(
        private val binding: ItemSimplePosterWithTitleBinding,
        private val clickListener: ((itemId: Int, type: ItemType, leastOneMovieId: Int, releaseDate: String) -> Unit)?,
        private val oscarCategory: OscarCategory?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            when(obj) {
                is MovieModel -> {
                    with(binding) {
                        Picasso.with(root.context).load(obj.getPosterUrl()).into(moviePosterWithTitleImage)
                        moviePosterWithTitleTitle.text = obj.title
                        moviePosterWithTitleRelease.text = obj.releaseDate?.let { dateFromAmericanFormatToDateWithMonthName(it) } ?: ""

                        binding.moviePosterWithTitleImage.setOnClickListener {
                            clickListener?.invoke(obj.id, ItemType.MOVIE, 0, obj.releaseDate ?: "")
                        }
                    }
                }
                is SpecialItem -> {
                    with(binding) {
                        Picasso.with(root.context).load(obj.imageUrl).into(moviePosterWithTitleImage)
                        moviePosterWithTitleTitle.text = obj.title
                        if(obj.type == ItemType.MOVIE.name &&
                            oscarCategory != OscarCategory.BEST_FOREIGN_LANGUAGE_FILM &&
                            oscarCategory != OscarCategory.BEST_ORIGINAL_SONG
                        ) {
                            obj.releaseDate.let { moviePosterWithTitleRelease.text = dateFromAmericanFormatToDateWithMonthName(it) }
                        } else {
                            moviePosterWithTitleRelease.text = obj.subtitle
                        }

                        if(obj.categoriesWinner.isNotEmpty()) {
                            binding.moviePosterStatusParent.visibility = if(obj.categoriesWinner.contains(oscarCategory?.name)) {
                                 View.VISIBLE
                            } else {
                                View.GONE
                            }
                        }

                        binding.root.setOnClickListener {
                            clickListener?.invoke(obj.itemId, ItemType.valueOf(obj.type), obj.leastOneMovieId, obj.releaseDate)
                        }
                    }
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       clickListener: ((itemId: Int, type: ItemType, leastOneMovieId: Int, releaseDate: String) -> Unit)?,
                       oscarCategory: OscarCategory?
            ): SimplePosterWithTitleViewHolder {
                val binding = ItemSimplePosterWithTitleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return SimplePosterWithTitleViewHolder(binding, clickListener, oscarCategory)
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