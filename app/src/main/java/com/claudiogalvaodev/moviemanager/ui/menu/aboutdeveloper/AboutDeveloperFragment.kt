package com.claudiogalvaodev.moviemanager.ui.menu.aboutdeveloper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.databinding.FragmentAboutDeveloperBinding

class AboutDeveloperFragment: Fragment() {

    private val binding by lazy {
        FragmentAboutDeveloperBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentAboutDeveloperInstagramSocialMediaCard.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/claudiogaalvao/?hl=pt-br"))
            startActivity(browserIntent)
        }

        binding.fragmentAboutDeveloperLinkedinSocialMediaCard.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/claudiogaalvao/"))
            startActivity(browserIntent)
        }
    }

}