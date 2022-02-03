package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.model.MenuItem
import com.claudiogalvaodev.moviemanager.databinding.ItemMenuBinding
import com.claudiogalvaodev.moviemanager.utils.enums.MenuItemType

class MenuAdapter: ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(DIFF_CALLBACK) {

    var onClickListener: ((itemType: MenuItemType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MenuViewHolder(
        private val binding: ItemMenuBinding,
        private val clickListener: ((itemType: MenuItemType) -> Unit)?
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuItem) {
            binding.itemMenuIcon.setImageResource(item.iconId)
            binding.itemMenuTitle.text = item.title

            binding.root.setOnClickListener {
                clickListener?.invoke(item.type)
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((itemType: MenuItemType) -> Unit)?): MenuViewHolder {
                val binding = ItemMenuBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return MenuViewHolder(binding, clickListener)
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