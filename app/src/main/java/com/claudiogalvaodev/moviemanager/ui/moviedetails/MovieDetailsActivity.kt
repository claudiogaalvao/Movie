package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.claudiogalvaodev.moviemanager.databinding.ActivityMovieDetailsBinding
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModel()
    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    private val args: MovieDetailsActivityArgs by navArgs()
    private val movie: MovieEntity by lazy {
        args.movie
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activityMovieDetailsTitle.text = movie.title
        binding.activityMovieDetailsRelease.text = movie.release_date
        binding.activityMovieDetailsRate.text = movie.vote_average.toString()
        binding.activityMovieDetailsSinopse.text = movie.overview
        Picasso.with(binding.root.context).load(movie.getPoster()).into(binding.activityMovieDetailsCover)
    }

}