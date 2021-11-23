package com.claudiogalvaodev.filmes.ui.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.claudiogalvaodev.filmes.R
import com.claudiogalvaodev.filmes.databinding.ActivityMovieDetailsBinding
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.ui.viewmodel.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModel()
    private lateinit var movie: MovieEntity
    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        movie = intent.getSerializableExtra("filme") as MovieEntity

        binding.activityMovieDetailsTitle.text = movie.title
        binding.activityMovieDetailsRelease.text = movie.release_date
        binding.activityMovieDetailsRate.text = movie.vote_average.toString()
        binding.activityMovieDetailsSinopse.text = movie.overview
        Picasso.with(binding.root.context).load(movie.getCoverage()).into(binding.activityMovieDetailsCover)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_details_menu, menu)

        val filledHeart = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_filled, null)
        val emptyHeart = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_empty, null)
        // TODO ajustar para chamada assíncrona
        menu?.getItem(0)?.icon = if(viewModel.isFavorite(movie)) filledHeart else emptyHeart
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.movie_details_menu_add_favourite) {
            item.icon = changeIconFavoriteAccordingTo(item.icon)
        }
        return true
    }

    private fun changeIconFavoriteAccordingTo(currentIcon: Drawable): Drawable? {
        val filledHeart = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_filled, null)
        val emptyHeart = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_empty, null)
        if(currentIcon.constantState == emptyHeart?.constantState) {
            Toast.makeText(this, "Adicionado aos favoritos", Toast.LENGTH_LONG).show()
            viewModel.insertFavoriteMovie(movie)
            return filledHeart
        }
        viewModel.deleteFavoriteMovie(movie)
        Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_LONG).show()
        return emptyHeart
    }
}