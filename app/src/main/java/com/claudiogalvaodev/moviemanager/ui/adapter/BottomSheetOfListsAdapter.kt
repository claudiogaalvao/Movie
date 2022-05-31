package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemOptionsMyListsBinding
import com.claudiogalvaodev.moviemanager.ui.model.BottomSheetOfListsUI
import com.claudiogalvaodev.moviemanager.ui.model.SaveOn

class BottomSheetOfListsAdapter: ListAdapter<BottomSheetOfListsUI,
        BottomSheetOfListsAdapter.MyListsViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((listSelected: BottomSheetOfListsUI, action: Action) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListsViewHolder {
        return MyListsViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: MyListsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyListsViewHolder(
        private val binding: ItemOptionsMyListsBinding,
        private val clickListener: ((listSelected: BottomSheetOfListsUI, action: Action) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BottomSheetOfListsUI) {
            binding.itemOptionsMyListsName.text = item.name

            val action = if(item.isSaved) {
                binding.itemOptionsMyListsSavedMark.visibility = View.VISIBLE
                Action.REMOVE
            } else Action.INSERT

            binding.root.setOnClickListener {
                clickListener?.invoke(item, action)
            }

        }

        companion object {
            fun create(
                parent: ViewGroup,
                clickListener: ((listSelected: BottomSheetOfListsUI, action: Action) -> Unit)?
            ): MyListsViewHolder {
                val binding = ItemOptionsMyListsBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return MyListsViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        enum class Action {
            INSERT, REMOVE
        }

        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<BottomSheetOfListsUI>() {
            override fun areItemsTheSame(
                oldItem: BottomSheetOfListsUI,
                newItem: BottomSheetOfListsUI
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: BottomSheetOfListsUI,
                newItem: BottomSheetOfListsUI
            ): Boolean = oldItem.id == newItem.id
        }
    }

}