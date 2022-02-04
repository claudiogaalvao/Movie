package com.claudiogalvaodev.moviemanager.ui.mylists

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.claudiogalvaodev.moviemanager.R

class MyListsFragment : Fragment() {

    companion object {
        fun newInstance() = MyListsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_lists, container, false)
    }

}