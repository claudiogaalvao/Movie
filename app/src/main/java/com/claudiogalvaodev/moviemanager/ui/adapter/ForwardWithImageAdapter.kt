package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.databinding.ItemForwardWithImageBinding
import com.squareup.picasso.Picasso

class ForwardWithImageAdapter: ListAdapter<MyList, ForwardWithImageAdapter.ForwardWithImageViewHolder>(
    DIFF_CALLBACK) {

    var onItemClick: ((myList: MyList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForwardWithImageViewHolder {
        return ForwardWithImageViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ForwardWithImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForwardWithImageViewHolder(
        private val binding: ItemForwardWithImageBinding,
        private val clickListener: ((myList: MyList) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(myList: MyList) {
            binding.itemForwardTitle.text = myList.name
            myList.posterPath?.let { posterPath ->
                if(posterPath.isNotBlank()) {
                    Picasso.with(binding.root.context).load(myList.posterPath).into(binding.itemForwardImagePreview)
                } else {
                    binding.itemForwardImagePreview.setImageDrawable(null)
                }
            }

            binding.root.setOnClickListener {
                clickListener?.invoke(myList)
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       clickListener: ((myList: MyList) -> Unit)?
            ):ForwardWithImageViewHolder {
                val binding = ItemForwardWithImageBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return ForwardWithImageViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MyList>() {
            override fun areItemsTheSame(oldItem: MyList, newItem: MyList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MyList,
                newItem: MyList
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}