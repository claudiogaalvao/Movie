package com.claudiogalvaodev.moviemanager.ui.menu.aboutdeveloper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityAboutDeveloperBinding

class AboutDeveloperActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityAboutDeveloperBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Filmes)
        setContentView(binding.root)

        configToolbar()
        setToolbarTitle()
        initializeFragment()
    }

    private fun configToolbar() {
        binding.activityAboutDeveloperToolbar.setNavigationIcon(R.drawable.ic_back)
        binding.activityAboutDeveloperToolbar.setNavigationContentDescription(R.string.icon_back)
        setSupportActionBar(binding.activityAboutDeveloperToolbar)
    }

    private fun initializeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_about_developer_nav_host_fragment, AboutDeveloperFragment())
        transaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
    }

    private fun setToolbarTitle() {
        binding.activityAboutDeveloperToolbarTitle.text = getString(R.string.about_developer)
    }

}