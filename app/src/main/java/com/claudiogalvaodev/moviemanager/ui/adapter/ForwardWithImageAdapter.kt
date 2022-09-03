package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemForwardWithImageBinding
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.squareup.picasso.Picasso

class ForwardWithImageAdapter: ListAdapter<CustomListModel, ForwardWithImageAdapter.ForwardWithImageViewHolder>(
    DIFF_CALLBACK) {

    var onItemClick: ((customListEntity: CustomListModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForwardWithImageViewHolder {
        return ForwardWithImageViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ForwardWithImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForwardWithImageViewHolder(
        private val binding: ItemForwardWithImageBinding,
        private val clickListener: ((customList: CustomListModel) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(customList: CustomListModel) {
            binding.itemForwardTitle.text = customList.name

            if (customList.posterPath.isNotBlank()) {
                val posterUrl = customList.getPosterUrl()
                Picasso.with(binding.root.context).load(posterUrl)
                    .into(binding.itemForwardImagePreview)
            } else binding.itemForwardImagePreview.setImageDrawable(null)

            binding.root.setOnClickListener {
                clickListener?.invoke(customList)
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       clickListener: ((customListEntity: CustomListModel) -> Unit)?
            ):ForwardWithImageViewHolder {
                val binding = ItemForwardWithImageBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return ForwardWithImageViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<CustomListModel>() {
            override fun areItemsTheSame(oldItem: CustomListModel, newItem: CustomListModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CustomListModel,
                newItem: CustomListModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}