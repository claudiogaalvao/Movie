package com.claudiogalvaodev.moviemanager.ui.customLists

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentMyListsBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.ForwardWithImageAdapter
import com.claudiogalvaodev.moviemanager.ui.customLists.details.CustomListsActivity
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.utils.extensions.launchWhenResumed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("InflateParams")
class CustomListsFragment : Fragment() {

    private val viewModel: CustomListsViewModel by viewModel()
    private val binding by lazy {
        FragmentMyListsBinding.inflate(layoutInflater)
    }

    private lateinit var customListsAdapter: ForwardWithImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupAdapter() {
        customListsAdapter = ForwardWithImageAdapter().apply {
            onItemClick = { myList ->
                goToMyListDetails(myList)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.myListsRecyclerview.adapter = customListsAdapter
    }

    private fun setupObservers() {
        launchWhenResumed {
            viewModel.customLists.collectLatest { allMyLists ->
                setMyLists(allMyLists)
            }
        }
    }

    private fun setMyLists(customList: List<CustomListModel>) {
        customListsAdapter.submitList(customList)
    }

    private fun setupListeners() {
        binding.myListsFab.setOnClickListener {
            openCreateNewListDialog()
        }
    }

    private fun openCreateNewListDialog() {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.MyDialogTheme)

            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_mylists_form, null)
            val myListEditText = dialogView?.findViewById<EditText>(R.id.my_lists_form_edittext)

            builder.setTitle(getString(R.string.new_list_dialog_title))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.new_list_dialog_button)) { _, _ ->
                    val newListName = myListEditText?.text.toString()
                    viewModel.createNewList(newListName)
                }
                .setNegativeButton(resources.getString(R.string.filter_alertdialog_negative), null)
                .show()
        }
    }

    private fun goToMyListDetails(customList: CustomListModel) {
        context?.let {
            startActivity(CustomListsActivity.newInstance(it, customList.id, customList.name))
        }
    }

}