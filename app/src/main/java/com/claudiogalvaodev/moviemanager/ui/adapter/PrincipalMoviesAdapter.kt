package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.databinding.ItemPrincipalBackdropBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.PrincipalMoviesAdapter.*
import com.squareup.picasso.Picasso

class PrincipalMoviesAdapter: ListAdapter<MovieModel, PrincipalMoviesViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((movieModel: MovieModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrincipalMoviesViewHolder {
        return PrincipalMoviesViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PrincipalMoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PrincipalMoviesViewHolder(
        private val binding: ItemPrincipalBackdropBinding,
        private val clickListener: ((movieModel: MovieModel) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(movieModel: MovieModel) {
            with(binding) {
                 Picasso.with(root.context).load(movieModel.getBackdropUrl()).into(principalCoverImage)
                principalCoverTitle.text = movieModel.title

                binding.principalCoverImage.setOnClickListener {
                    clickListener?.invoke(movieModel)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((movieModel: MovieModel) -> Unit)?): PrincipalMoviesViewHolder {
                val binding = ItemPrincipalBackdropBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return PrincipalMoviesViewHolder(binding, clickListener)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}