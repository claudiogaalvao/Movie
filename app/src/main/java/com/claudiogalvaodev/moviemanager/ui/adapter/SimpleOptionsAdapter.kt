package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemRoundedOptionBinding

class SimpleOptionsAdapter: ListAdapter<String, SimpleOptionsAdapter.SimpleOptionsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleOptionsViewHolder {
        return SimpleOptionsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SimpleOptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SimpleOptionsViewHolder(
        private val binding: ItemRoundedOptionBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.itemText.text = item
        }

        companion object {
            fun create(parent: ViewGroup): SimpleOptionsViewHolder {
                val binding = ItemRoundedOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return SimpleOptionsViewHolder(binding)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

}