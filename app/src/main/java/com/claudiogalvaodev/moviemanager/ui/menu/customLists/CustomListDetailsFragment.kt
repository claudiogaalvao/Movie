package com.claudiogalvaodev.moviemanager.ui.menu.customLists

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentCustomListDetailsBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class CustomListDetailsFragment: Fragment() {

    private val viewModel: CustomListsViewModel by viewModel()
    private val binding by lazy {
        FragmentCustomListDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var moviesAdapter: SimplePosterAdapter

    private val args: CustomListDetailsFragmentArgs by navArgs()
    private val listId by lazy {
        args.myListId
    }
    private val listName by lazy {
        args.myListName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
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

        (activity as CustomListsActivity).setToolbarTitle(listName)

        getData()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.my_list_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delete_list -> {
                deleteMyList()
                true
            }
            else -> {
                findNavController().popBackStack()
                true
            }
        }
    }

    private fun deleteMyList() {
        lifecycleScope.launch {
            confirmToDeleteDialog()
        }
    }

    private fun confirmToDeleteDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(resources.getString(R.string.filter_alertdialog_title))
                .setMessage(resources.getString(R.string.filter_alertdialog_delete_mylist_message))
                .setPositiveButton(resources.getString(R.string.filter_alertdialog_positive)) { _, _ ->
                    viewModel.deleteMyList(listId)
                    Toast.makeText(context, getString(R.string.list_deleted_successfully_message), Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
                .setNegativeButton(resources.getString(R.string.filter_alertdialog_negative), null)
                .show()
        }
    }

    private fun getData() {
        viewModel.getMoviesByListId(listId)
    }

    private fun setupAdapter() {
        moviesAdapter = SimplePosterAdapter().apply {
            onItemClick = { movieId ->
                goToMovieDetails(movieId)
            }
        }
    }

    private fun setupRecyclerView() {
        val layout = GridLayoutManager(context, calcNumberOfColumns())
        binding.myListDetailsRecyclerview.apply {
            layoutManager = layout
            adapter = moviesAdapter
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest { movies ->
                moviesAdapter.submitList(movies)
            }
        }
    }

    private fun goToMovieDetails(movieId: Int) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }

    private fun calcNumberOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val spaceBetween = 12
        val marginStart = 16
        val marginEnd = 16
        val widthEachImage = 120

        var countImages = dpWidth - marginStart - marginEnd
        countImages /= (widthEachImage+spaceBetween)
        return countImages.roundToInt()
    }
}