package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemFilterBinding
import com.claudiogalvaodev.moviemanager.ui.model.FilterModel
import com.claudiogalvaodev.moviemanager.ui.adapter.FilterAdapter.FilterViewHolder

class FilterAdapter: ListAdapter<FilterModel, FilterViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((filter: FilterModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FilterViewHolder(
        private val binding: ItemFilterBinding,
        private val clickListener: ((filter: FilterModel) -> Unit)?
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: FilterModel) {
            binding.itemFilterButton.text = filter.name
            binding.itemFilterButton.isSelected = filter.currentValue.isNotBlank()

            binding.itemFilterButton.setOnClickListener {
                clickListener?.invoke(filter)
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((filter: FilterModel) -> Unit)?): FilterViewHolder {
                val binding = ItemFilterBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return FilterViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<FilterModel>() {
            override fun areItemsTheSame(oldItem: FilterModel, newItem: FilterModel): Boolean {
                return oldItem.currentValue == newItem.currentValue
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FilterModel, newItem: FilterModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}