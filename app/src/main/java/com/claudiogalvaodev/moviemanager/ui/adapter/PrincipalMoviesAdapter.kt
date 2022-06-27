package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.ItemPrincipalBackdropBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.PrincipalMoviesAdapter.*
import com.squareup.picasso.Picasso

class PrincipalMoviesAdapter: ListAdapter<Movie, PrincipalMoviesViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((movie: Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrincipalMoviesViewHolder {
        return PrincipalMoviesViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PrincipalMoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PrincipalMoviesViewHolder(
        private val binding: ItemPrincipalBackdropBinding,
        private val clickListener: ((movie: Movie) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getBackdropUrl()).into(principalCoverImage)
                principalCoverTitle.text = movie.title

                binding.principalCoverImage.setOnClickListener {
                    clickListener?.invoke(movie)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((movie: Movie) -> Unit)?): PrincipalMoviesViewHolder {
                val binding = ItemPrincipalBackdropBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return PrincipalMoviesViewHolder(binding, clickListener)
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