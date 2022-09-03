package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemSmallImageCircleBinding
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.claudiogalvaodev.moviemanager.ui.model.ProductionCompanyModel
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import com.squareup.picasso.Picasso

// TODO Use view type implementation here
class CircleAdapter: ListAdapter<Any, CircleAdapter.CircleAdapterViewHolder>(DIFF_CALLBACK) {

    var onClickListener: ((personSelected: PersonModel) -> Unit)? = null
    var onLongClickListener: ((imageDescription: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleAdapterViewHolder {
        return CircleAdapterViewHolder.create(parent, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: CircleAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CircleAdapterViewHolder(
        private val binding: ItemSmallImageCircleBinding,
        private val clickListener: ((personSelected: PersonModel) -> Unit)?,
        private val longClickListener: ((imageDescription: String) -> Unit)?
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            with(binding) {
                when(obj) {
                    is PersonModel -> {
                        Picasso.with(root.context).load(obj.getProfileImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                    is ProductionCompanyModel -> {
                        itemImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                        Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                    is ProviderModel -> {
                        itemImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                        Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                }
            }

            setupClickListener(obj)
            setupLongClickListener(obj)
        }

        private fun setupClickListener(obj: Any) {
            if (obj is PersonModel) {
                binding.root.setOnClickListener {
                    clickListener?.invoke(obj)
                }
            }
        }

        private fun setupLongClickListener(obj: Any) {
            binding.root.setOnLongClickListener {
                when (obj) {
                    is PersonModel -> longClickListener?.invoke(obj.name)
                    is ProductionCompanyModel -> longClickListener?.invoke(obj.name)
                    is ProviderModel -> longClickListener?.invoke(obj.name)
                }
                true
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       clickListener: ((personSelected: PersonModel) -> Unit)?,
                       longClickListener: ((imageDescription: String) -> Unit)?,
            ): CircleAdapterViewHolder {
                val binding = ItemSmallImageCircleBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return CircleAdapterViewHolder(binding, clickListener, longClickListener)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

        }
    }
}