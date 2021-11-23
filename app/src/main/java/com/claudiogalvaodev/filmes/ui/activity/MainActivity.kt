package com.claudiogalvaodev.filmes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.filmes.R
import com.claudiogalvaodev.filmes.databinding.ActivityMainBinding
import com.claudiogalvaodev.filmes.ui.fragment.FavouriteFragment
import com.claudiogalvaodev.filmes.ui.fragment.HomeFragment

class MainActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val favouriteFragment = FavouriteFragment()

        setCurrentFragment(homeFragment)

        binding.activityMainBottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.favourites -> setCurrentFragment(favouriteFragment)
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