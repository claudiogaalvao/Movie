package com.claudiogalvaodev.filmes.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.databinding.ItemSimplePosterWithTitleBinding
import com.claudiogalvaodev.filmes.ui.activity.MovieDetailsActivity
import com.claudiogalvaodev.filmes.utils.format.formatDateUtils.fromAmericanFormatToDateWithMonthName
import com.squareup.picasso.Picasso

class SimplePosterWithTitleMoviesAdapter(
    private var movies: List<MovieEntity>
): RecyclerView.Adapter<SimplePosterWithTitleMoviesAdapter.ViewHolder>() {

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

    class ViewHolder(val binding: ItemSimplePosterWithTitleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getPoster()).into(moviePosterWithTitleImage)
                moviePosterWithTitleTitle.text = movie.title
                moviePosterWithTitleRelease.text = fromAmericanFormatToDateWithMonthName(movie.release_date)

                binding.moviePosterWithTitleImage.setOnClickListener {
                    goToMovieDetails(movie)
                }
            }
        }

        private fun goToMovieDetails(movie: MovieEntity) {
            val intent = Intent(binding.root.context, MovieDetailsActivity::class.java)
            intent.putExtra("filme", movie)
            binding.root.context.startActivity(intent)
        }
    }
}