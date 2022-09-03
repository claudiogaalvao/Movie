package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemPreviewVideoWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.model.VideoModel
import com.squareup.picasso.Picasso

class VideoPreviewAdapter: ListAdapter<VideoModel, VideoPreviewAdapter.VideoPreviewViewHolder>(
    DIFF_CALLBACK) {

    var onItemClick: ((videoId: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPreviewViewHolder {
        return VideoPreviewViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: VideoPreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VideoPreviewViewHolder(
        private val binding: ItemPreviewVideoWithTitleBinding,
        private val clickListener: ((videoId: String) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(video: VideoModel) {
            with(binding) {
                Picasso.with(root.context).load(video.getThumbnailUrl()).into(thumbnailImage)
                moviePosterWithTitleTitle.text = video.name

                root.setOnClickListener {
                    clickListener?.invoke(video.key)
                }
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                clickListener: ((videoId: String) -> Unit)?
            ): VideoPreviewViewHolder {
                val binding = ItemPreviewVideoWithTitleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return VideoPreviewViewHolder(binding, clickListener)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoModel>() {
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}