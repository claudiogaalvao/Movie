package com.claudiogalvaodev.moviemanager.ui.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityFiltersBinding
import com.claudiogalvaodev.moviemanager.model.Filter
import com.claudiogalvaodev.moviemanager.utils.enum.FilterType

class FiltersActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityFiltersBinding.inflate(layoutInflater)
    }

    private var filterSelected: Filter? = null
    private lateinit var currentValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        filterSelected = intent.getParcelableExtra(KEY_FILTER)
        currentValue = filterSelected?.currentValue.orEmpty()

        configToolbar()
        initializeFragment()
    }

    private fun configToolbar() {
        binding.activityFilterToolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(binding.activityFilterToolbar)
    }

    private fun initializeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_filter_nav_host, getFragment())
        transaction.commit()
    }

    private fun getFragment(): Fragment {
        val fragment = when(filterSelected?.type) {
            FilterType.SORT_BY -> FilterOrderByFragment()
            FilterType.GENRES -> FilterGenresFragment()
            else -> throw Exception("Unrecognize filter type to select a fragment")
        }

        val args = Bundle()
        args.putString(KEY_BUNDLE_CURRENT_VALUE, currentValue)
        fragment.arguments = args
        return fragment
    }

    fun changeCurrentValue(newValue: String) {
        currentValue = newValue
    }

    override fun onSupportNavigateUp(): Boolean {
        checkAndNavigateToPreviousActivity()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        checkAndNavigateToPreviousActivity()
    }

    fun checkAndNavigateToPreviousActivity() {
        getFilterSelected()
        finish()
    }

    fun getFilterSelected() {
        val newFilter = filterSelected
        newFilter?.currentValue = currentValue
        setResult(RESULT_OK, Intent().putExtra(KEY_RESULT, newFilter))
    }

    fun setToolbarTitle(title: String) {
        binding.activityFilterToolbarTitle.text = title
    }

    class Contract: ActivityResultContract<Filter, Filter?>() {
        override fun createIntent(context: Context, input: Filter?): Intent {
            return Intent(context, FiltersActivity::class.java).putExtra(KEY_FILTER, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Filter? {
            return intent?.getParcelableExtra(KEY_RESULT)
        }

    }

    companion object {
        const val KEY_BUNDLE_CURRENT_VALUE = "CURRENT_VALUE"
        private const val KEY_FILTER = "FILTER"
        private const val KEY_RESULT = "RESULT"
    }

}