package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.OscarNomination
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.ItemSimplePosterWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter.*
import com.claudiogalvaodev.moviemanager.utils.enums.ItemType
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils.dateFromAmericanFormatToDateWithMonthName
import com.squareup.picasso.Picasso

class SimplePosterWithTitleAdapter: ListAdapter<Any, SimplePosterWithTitleViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((itemId: Int, type: ItemType, leastOneMovieId: Int) -> Unit)? = null
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
        private val clickListener: ((itemId: Int, type: ItemType, leastOneMovieId: Int) -> Unit)?,
        private val oscarCategory: OscarCategory?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            when(obj) {
                is Movie -> {
                    with(binding) {
                        Picasso.with(root.context).load(obj.getPoster()).into(moviePosterWithTitleImage)
                        moviePosterWithTitleTitle.text = obj.title
                        moviePosterWithTitleRelease.text = dateFromAmericanFormatToDateWithMonthName(obj.release_date)

                        binding.moviePosterWithTitleImage.setOnClickListener {
                            clickListener?.invoke(obj.id, ItemType.MOVIE, 0)
                        }
                    }
                }
                is OscarNomination -> {
                    with(binding) {
                        Picasso.with(root.context).load(obj.imageUrl).into(moviePosterWithTitleImage)
                        moviePosterWithTitleTitle.text = obj.title
                        if(obj.type == ItemType.MOVIE &&
                            oscarCategory != OscarCategory.BEST_FOREIGN_LANGUAGE_FILM &&
                            oscarCategory != OscarCategory.BEST_ORIGINAL_SONG
                        ) {
                            obj.releaseDate?.let { moviePosterWithTitleRelease.text = dateFromAmericanFormatToDateWithMonthName(it) }
                        } else {
                            moviePosterWithTitleRelease.text = obj.subtitle
                        }

                        if(obj.categoriesWinner.isNotEmpty()) {
                            binding.moviePosterStatusParent.visibility = if(obj.categoriesWinner.contains(oscarCategory)) {
                                 View.VISIBLE
                            } else {
                                View.GONE
                            }
                        }

                        binding.moviePosterWithTitleImage.setOnClickListener {
                            clickListener?.invoke(obj.itemId, obj.type, obj.leastOneMovieId ?: 0)
                        }
                    }
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       clickListener: ((itemId: Int, type: ItemType, leastOneMovieId: Int) -> Unit)?,
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