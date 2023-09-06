package com.claudiogalvaodev.moviemanager.ui.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityFiltersBinding
import com.claudiogalvaodev.moviemanager.ui.model.FilterModel
import com.claudiogalvaodev.moviemanager.utils.enums.FilterType

class FiltersActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityFiltersBinding.inflate(layoutInflater)
    }

    private var filterSelected: FilterModel? = null
    private lateinit var currentValue: String
    private lateinit var newCurrentValue: String

    private val alertDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.filter_alertdialog_title))
            .setMessage(resources.getString(R.string.filter_alertdialog_message))
            .setPositiveButton(resources.getString(R.string.filter_alertdialog_positive)) { _, _ ->
                finish()
            }
            .setNegativeButton(resources.getString(R.string.filter_alertdialog_negative), null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Filmes)
        setContentView(binding.root)

        filterSelected = intent.getParcelableExtra(KEY_FILTER)
        currentValue = filterSelected?.currentValue.orEmpty()
        newCurrentValue = filterSelected?.currentValue.orEmpty()

        configToolbar()
        initializeFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if(filterSelected?.type != FilterType.SORT_BY) {
            if(currentValue.isNotBlank()) menuInflater.inflate(R.menu.filter_options_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter_reset -> {
                changeCurrentValue("")
                saveChangesAndNavigateToPreviousActivity()
                true
            }
            else -> {
                showAlertDialogOrNavigate()
                true
            }
        }
    }

    private fun configToolbar() {
        binding.activityFilterToolbar.setNavigationIcon(R.drawable.ic_back)
        binding.activityFilterToolbar.setNavigationContentDescription(R.string.icon_back)
        setSupportActionBar(binding.activityFilterToolbar)
    }

    private fun initializeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_filter_nav_host, getFragment())
        transaction.commit()
    }

    private fun getFragment(): Fragment {
        val fragment = when(filterSelected?.type) {
            FilterType.SORT_BY -> {
                setToolbarTitle(resources.getString(R.string.fragment_orderby_title))
                FilterOrderByFragment()
            }
            FilterType.GENRES -> {
                setToolbarTitle(resources.getString(R.string.fragment_genres_title))
                FilterGenresFragment()
            }
            FilterType.PEOPLE -> {
                setToolbarTitle(resources.getString(R.string.fragment_people_title))
                FilterPeopleFragment()
            }
            FilterType.YEARS -> {
                setToolbarTitle(resources.getString(R.string.filter_type_years))
                FilterYearFragment()
            }
            FilterType.RUNTIME -> {
                setToolbarTitle(resources.getString(R.string.fragment_filter_runtime_title))
                FilterRuntimeFragment()
            }
            else -> throw Exception("Unrecognize filter type to select a fragment")
        }

        val args = Bundle()
        args.putString(KEY_BUNDLE_CURRENT_VALUE, currentValue)
        fragment.arguments = args
        return fragment
    }

    fun changeCurrentValue(newValue: String) {
        newCurrentValue = newValue
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showAlertDialogOrNavigate()
    }

    override fun onSupportNavigateUp(): Boolean {
        showAlertDialogOrNavigate()
        return super.onSupportNavigateUp()
    }

    private fun showAlertDialogOrNavigate() {
        if(currentValue != newCurrentValue) {
            alertDialog.show()
        } else {
            finish()
        }
    }

    fun saveChangesAndNavigateToPreviousActivity() {
        getFilterSelected()
        finish()
    }

    private fun getFilterSelected() {
        val newFilter = filterSelected
        newFilter?.currentValue = newCurrentValue
        setResult(RESULT_OK, Intent().putExtra(KEY_RESULT, newFilter))
    }

    private fun setToolbarTitle(title: String) {
        binding.activityFilterToolbarTitle.text = title
    }

    class Contract: ActivityResultContract<FilterModel, FilterModel?>() {
        override fun createIntent(context: Context, input: FilterModel): Intent {
            return Intent(context, FiltersActivity::class.java).putExtra(KEY_FILTER, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): FilterModel? {
            return intent?.getParcelableExtra(KEY_RESULT)
        }

    }

    companion object {
        const val KEY_BUNDLE_CURRENT_VALUE = "CURRENT_VALUE"
        private const val KEY_FILTER = "FILTER"
        private const val KEY_RESULT = "RESULT"
    }

}