package com.claudiogalvaodev.filmes.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.filmes.databinding.ActivityMovieItemBinding
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.ui.activity.MovieDetailsActivity
import com.squareup.picasso.Picasso

class MovieListAdapter(
    private val movies: List<MovieEntity>
): RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ActivityMovieItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = movies[position]

        holder.bind(filme)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(val binding: ActivityMovieItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            with(binding) {
                Picasso.with(root.context).load(movie.getCoverage()).into(activityMovieItemCover)
                binding.activityMovieItemCover.setOnClickListener {
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