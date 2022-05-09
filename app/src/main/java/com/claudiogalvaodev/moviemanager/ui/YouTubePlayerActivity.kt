package com.claudiogalvaodev.moviemanager.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityYoutubePlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

private const val ARG_VIDEO_ID = "videoId"

class YouTubePlayerActivity : YouTubeBaseActivity() {

    private val binding by lazy {
        ActivityYoutubePlayerBinding.inflate(layoutInflater)
    }

    private val videoId by lazy {
        intent.getStringExtra(ARG_VIDEO_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Filmes)
        setContentView(binding.root)

        videoId?.let {
            initializePlayer(it)
        }
    }

    private fun initializePlayer(videoId: String) {
        binding.youtubePlayer.initialize(YOUTUBE_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.let {
                    with(it) {
                        loadVideo(videoId)
                        play()
                    }
                }


            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        const val YOUTUBE_KEY = BuildConfig.YOUTUBE_API_KEY

        fun newInstance(context: Context, videoId: String) : Intent {
            val intent = Intent(context, YouTubePlayerActivity::class.java)
            intent.putExtra(ARG_VIDEO_ID, videoId)
            return intent
        }

    }

}