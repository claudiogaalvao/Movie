package com.claudiogalvaodev.moviemanager.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.claudiogalvaodev.moviemanager.databinding.ActivityMovieDetailsBinding
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.ui.viewmodel.MovieDetailsViewModel
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
        Picasso.with(binding.root.context).load(movie.getPoster()).into(binding.activityMovieDetailsCover)
    }

}