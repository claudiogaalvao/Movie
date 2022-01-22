package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemFilterBinding
import com.claudiogalvaodev.moviemanager.model.Filter
import com.claudiogalvaodev.moviemanager.ui.adapter.FilterAdapter.FilterViewHolder

class FilterAdapter: ListAdapter<Filter, FilterViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((filter: Filter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FilterViewHolder(
        private val binding: ItemFilterBinding,
        private val clickListener: ((filter: Filter) -> Unit)?
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: Filter) {
            binding.itemFilterButton.text = filter.name
            if(filter.currentValue.isBlank()) binding.itemFilterButton.isSelected = true

            binding.itemFilterButton.setOnClickListener {
                clickListener?.invoke(filter)
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((filter: Filter) -> Unit)?): FilterViewHolder {
                val binding = ItemFilterBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return FilterViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Filter>() {
            override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
                return oldItem == newItem
            }

        }
    }

}