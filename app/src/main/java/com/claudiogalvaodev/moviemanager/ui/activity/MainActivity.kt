package com.claudiogalvaodev.moviemanager.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityMainBinding
import com.claudiogalvaodev.moviemanager.ui.fragment.DiscoverFragment
import com.claudiogalvaodev.moviemanager.ui.fragment.HomeFragment

class MainActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val favouriteFragment = DiscoverFragment()

        setCurrentFragment(homeFragment)

        binding.activityMainBottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.discover -> setCurrentFragment(favouriteFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_main_framelayout, fragment)
            commit()
        }
    }
}