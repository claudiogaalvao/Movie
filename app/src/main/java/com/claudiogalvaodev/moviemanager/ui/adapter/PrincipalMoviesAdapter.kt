package com.claudiogalvaodev.moviemanager.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.databinding.ItemPrincipalBackdropBinding
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.squareup.picasso.Picasso

class PrincipalMoviesAdapter(
    private val movies: List<MovieEntity>
): RecyclerView.Adapter<PrincipalMoviesAdapter.ViewHolder>() {

    var onItemClick: ((movie: MovieEntity) -> Unit)? = null

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

    inner class ViewHolder(private val binding: ItemPrincipalBackdropBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            with(binding) {
                 Picasso.with(root.context).load(movie.getBackdrop()).into(principalCoverImage)
                principalCoverTitle.text = movie.title

                binding.principalCoverImage.setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }
}