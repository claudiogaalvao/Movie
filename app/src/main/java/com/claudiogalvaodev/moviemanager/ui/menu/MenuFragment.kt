package com.claudiogalvaodev.moviemanager.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.ui.model.MenuItem
import com.claudiogalvaodev.moviemanager.databinding.FragmentMenuBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.MenuAdapter
import com.claudiogalvaodev.moviemanager.ui.menu.aboutdeveloper.AboutDeveloperActivity
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ScheduledNotificationsActivity
import com.claudiogalvaodev.moviemanager.utils.enums.MenuItemType

class MenuFragment: Fragment() {

    private val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    private val menuItems: List<MenuItem> by lazy {
        listOf(
            MenuItem(
                iconId = R.drawable.ic_time,
                title = getString(R.string.scheduled_notifications),
                type = MenuItemType.SCHEDULED_NOTIFICATIONS,
                onClick = {
                    goToScheduledNotifications()
                }
            ),
            MenuItem(
                iconId = R.drawable.ic_code,
                title = getString(R.string.about_developer),
                type = MenuItemType.ABOUT_DEVELOPER,
                onClick = {
                    goToAboutDeveloper()
                }
            )
        )
    }

    private val menuItemsAdapter: MenuAdapter = MenuAdapter()

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
        setupRecyclerView()
    }

    private fun setVersionName() {
        val versionName = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        binding.fragmentMenuVersion.text = versionName
    }

    private fun setupRecyclerView() {
        binding.fragmentMenuRecyclerview.adapter = menuItemsAdapter
        menuItemsAdapter.submitList(menuItems)
    }

    private fun goToScheduledNotifications() {
        val intent = Intent(activity, ScheduledNotificationsActivity::class.java)
        startActivity(intent)
    }

    private fun goToAboutDeveloper() {
        val intent = Intent(activity, AboutDeveloperActivity::class.java)
        startActivity(intent)
    }

}