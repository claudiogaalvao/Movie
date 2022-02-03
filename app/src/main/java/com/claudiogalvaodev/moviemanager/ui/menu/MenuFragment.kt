package com.claudiogalvaodev.moviemanager.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.data.model.MenuItem
import com.claudiogalvaodev.moviemanager.databinding.FragmentMenuBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.MenuAdapter
import com.claudiogalvaodev.moviemanager.utils.enums.MenuItemType

class MenuFragment: Fragment() {

    private val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    private val menuItems: List<MenuItem> by lazy {
        listOf(
            MenuItem(iconId = R.drawable.ic_bookmark,
                title = getString(R.string.my_lists),
                type = MenuItemType.MY_LISTS),
            MenuItem(iconId = R.drawable.ic_code,
                title = getString(R.string.about_developer),
                type = MenuItemType.ABOUT_DEVELOPER),
            MenuItem(iconId = R.drawable.ic_settings,
                title = getString(R.string.settings),
                type = MenuItemType.SETTINGS),
        )
    }

    private lateinit var menuItemsAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setVersionName()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setVersionName() {
        val versionName = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        binding.fragmentMenuVersion.text = versionName
    }

    private fun setupAdapter() {
        menuItemsAdapter = MenuAdapter().apply {
            onClickListener = { menuItemType ->
                when(menuItemType) {
                    MenuItemType.MY_LISTS -> {
                        Toast.makeText(context, "Selecionou minhas listas", Toast.LENGTH_LONG).show()
                    }
                    MenuItemType.ABOUT_DEVELOPER -> {
                        Toast.makeText(context, "Selecionou about developer", Toast.LENGTH_LONG).show()
                    }
                    MenuItemType.SETTINGS -> {
                        Toast.makeText(context, "Selecionou settings", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.fragmentMenuRecyclerview.adapter = menuItemsAdapter
        menuItemsAdapter.submitList(menuItems)
    }

}