package com.claudiogalvaodev.moviemanager.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.databinding.ItemPrincipalBackdropBinding
import com.claudiogalvaodev.moviemanager.ui.activity.MovieDetailsActivity
import com.squareup.picasso.Picasso

class PrincipalMoviesAdapter(
    private val movies: List<MovieEntity>
): RecyclerView.Adapter<PrincipalMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPrincipalBackdropBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = movies[position]

        holder.bind(filme)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(val binding: ItemPrincipalBackdropBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getBackdrop()).into(principalCoverImage)
                principalCoverTitle.text = movie.title

                binding.principalCoverImage.setOnClickListener {
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