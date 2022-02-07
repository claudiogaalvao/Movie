package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.databinding.ItemOptionsMyListsBinding

class MyListsAdapter: ListAdapter<MyList, MyListsAdapter.MyListsViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((listSelected: MyList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListsViewHolder {
        return MyListsViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: MyListsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyListsViewHolder(
        private val binding: ItemOptionsMyListsBinding,
        private val clickListener: ((listSelected: MyList) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyList) {
            binding.itemOptionsMyListsName.text = item.name

            binding.root.setOnClickListener {
                clickListener?.invoke(item)
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickListener: ((listSelected: MyList) -> Unit)?): MyListsViewHolder {
                val binding = ItemOptionsMyListsBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return MyListsViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MyList>() {
            override fun areItemsTheSame(oldItem: MyList, newItem: MyList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MyList, newItem: MyList): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}