package com.claudiogalvaodev.moviemanager.ui.youtube

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityYoutubePlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

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
            initializePlayer(it, savedInstanceState)
        }
    }

    private fun initializePlayer(videoId: String, savedInstanceState: Bundle?) {
        binding.youtubePlayer.initialize(YOUTUBE_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                wasRestored: Boolean
            ) {
                setupPlayer(player, videoId, savedInstanceState)
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {
                sendError("Youtube initialization failure: ${result?.name}")
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                finish()
            }

        })
    }

    private fun setupPlayer(
        player: YouTubePlayer?,
        videoId: String,
        savedInstanceState: Bundle?
    ) {
        player?.let {
            with(it) {
                loadVideo(videoId)
                if (savedInstanceState == null) setFullscreen(true)
                setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
                    override fun onLoading() { }

                    override fun onLoaded(p0: String?) { }

                    override fun onAdStarted() { }

                    override fun onVideoStarted() { }

                    override fun onVideoEnded() {
                        finish()
                    }

                    override fun onError(error: YouTubePlayer.ErrorReason?) {
                        sendError("Youtube initialized successfully but get unknown error to load video: ${error?.name}")
                    }
                })
            }
        }
    }

    private fun sendError(message: String) {
        try {
            throw Exception(message)
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
        }
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