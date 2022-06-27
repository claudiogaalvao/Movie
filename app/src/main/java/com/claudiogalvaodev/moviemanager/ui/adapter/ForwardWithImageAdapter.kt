package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.UserListEntity
import com.claudiogalvaodev.moviemanager.databinding.ItemForwardWithImageBinding
import com.squareup.picasso.Picasso

class ForwardWithImageAdapter: ListAdapter<UserListEntity, ForwardWithImageAdapter.ForwardWithImageViewHolder>(
    DIFF_CALLBACK) {

    var onItemClick: ((userListEntity: UserListEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForwardWithImageViewHolder {
        return ForwardWithImageViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ForwardWithImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForwardWithImageViewHolder(
        private val binding: ItemForwardWithImageBinding,
        private val clickListener: ((userListEntity: UserListEntity) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(userListEntity: UserListEntity) {
            binding.itemForwardTitle.text = userListEntity.name

            if (userListEntity.movies.isNotEmpty()) {
                val posterUrl = userListEntity.movies.last().getPosterUrl()
                Picasso.with(binding.root.context).load(posterUrl)
                    .into(binding.itemForwardImagePreview)
            } else binding.itemForwardImagePreview.setImageDrawable(null)

            binding.root.setOnClickListener {
                clickListener?.invoke(userListEntity)
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       clickListener: ((userListEntity: UserListEntity) -> Unit)?
            ):ForwardWithImageViewHolder {
                val binding = ItemForwardWithImageBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return ForwardWithImageViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<UserListEntity>() {
            override fun areItemsTheSame(oldItem: UserListEntity, newItem: UserListEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserListEntity,
                newItem: UserListEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}