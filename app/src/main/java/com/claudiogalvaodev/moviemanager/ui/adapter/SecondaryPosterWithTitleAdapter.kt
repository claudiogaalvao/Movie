package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.ui.model.SpecialItem
import com.claudiogalvaodev.moviemanager.databinding.ItemSecondayPosterWithTitleBinding
import com.squareup.picasso.Picasso

class SecondaryPosterWithTitleAdapter: ListAdapter<SpecialItem, SecondaryPosterWithTitleAdapter.SecondaryPosterWithTitleViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((movieId: Int, releaseDate: String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondaryPosterWithTitleViewHolder {
        return SecondaryPosterWithTitleViewHolder.create(parent = parent, clickListener = onItemClick)
    }

    override fun onBindViewHolder(holder: SecondaryPosterWithTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SecondaryPosterWithTitleViewHolder(
        private val binding: ItemSecondayPosterWithTitleBinding,
        private val clickListener: ((movieId: Int, releaseDate: String) -> Unit)? = null
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: SpecialItem) {
            with(binding) {
                Picasso.with(root.context).load(movie.imageUrl).into(moviePosterWithTitleImage)
                moviePosterWithTitleTitle.text = movie.title

                binding.moviePosterStatusParent.visibility = View.GONE

                binding.root.setOnClickListener {
                    clickListener?.invoke(movie.itemId, movie.releaseDate)
                }
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                clickListener: ((movieId: Int, releaseDate: String) -> Unit)?
            ): SecondaryPosterWithTitleViewHolder {
                val binding = ItemSecondayPosterWithTitleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return SecondaryPosterWithTitleViewHolder(binding, clickListener)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SpecialItem>() {
            override fun areItemsTheSame(oldItem: SpecialItem, newItem: SpecialItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SpecialItem, newItem: SpecialItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}