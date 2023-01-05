package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.ui.model.MenuItem
import com.claudiogalvaodev.moviemanager.databinding.ItemMenuBinding

class MenuAdapter: ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MenuViewHolder(private val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuItem) {
            binding.itemMenuIcon.setImageResource(item.iconId)
            binding.itemMenuTitle.text = item.title

            binding.root.setOnClickListener {
                item.onClick.invoke()
            }
        }

        companion object {
            fun create(parent: ViewGroup): MenuViewHolder {
                val binding = ItemMenuBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return MenuViewHolder(binding)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MenuItem>() {
            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem.iconId == newItem.iconId
            }

        }
    }
}