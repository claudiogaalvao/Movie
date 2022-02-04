package com.claudiogalvaodev.moviemanager.ui.mylists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.claudiogalvaodev.moviemanager.R

class MyListsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lists)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyListsFragment.newInstance())
                .commitNow()
        }
    }
}