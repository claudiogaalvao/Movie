package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.model.Video
import com.claudiogalvaodev.moviemanager.databinding.ItemPreviewVideoWithTitleBinding

class PreviewVideoWithTitleAdapter: ListAdapter<Video, PreviewVideoWithTitleAdapter.PreviewVideoWithTitleViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((video: Video) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreviewVideoWithTitleViewHolder {
        return PreviewVideoWithTitleViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PreviewVideoWithTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PreviewVideoWithTitleViewHolder(
        private val binding: ItemPreviewVideoWithTitleBinding,
        private val clickListener: ((video: Video) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            with(binding) {
                
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                clickListener: ((video: Video) -> Unit)?
            ): PreviewVideoWithTitleViewHolder {
                val binding = ItemPreviewVideoWithTitleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return PreviewVideoWithTitleViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

        }
    }

}