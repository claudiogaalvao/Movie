package com.claudiogalvaodev.moviemanager.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.databinding.ItemSimplePosterWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.utils.format.formatDateUtils.fromAmericanFormatToDateWithMonthName
import com.squareup.picasso.Picasso

class SimplePosterWithTitleAdapter(
    private var movies: List<MovieEntity>
): RecyclerView.Adapter<SimplePosterWithTitleAdapter.ViewHolder>() {

    var onItemClick: ((movie: MovieEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSimplePosterWithTitleBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = movies[position]

        holder.bind(filme)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(val binding: ItemSimplePosterWithTitleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getPoster()).into(moviePosterWithTitleImage)
                moviePosterWithTitleTitle.text = movie.title
                moviePosterWithTitleRelease.text = fromAmericanFormatToDateWithMonthName(movie.release_date)

                binding.moviePosterWithTitleImage.setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }
}