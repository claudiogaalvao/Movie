package com.claudiogalvaodev.moviemanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemLargeImageCircleWithTitleBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter.CircleWithTitleViewHolder
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.claudiogalvaodev.moviemanager.ui.model.ProductionCompanyModel
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import com.squareup.picasso.Picasso

class CircleWithTitleAdapter: ListAdapter<Any, CircleWithTitleViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((obj: Any) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleWithTitleViewHolder {
        return CircleWithTitleViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: CircleWithTitleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CircleWithTitleViewHolder(
        private val binding: ItemLargeImageCircleWithTitleBinding,
        private val clickListener: ((obj: Any) -> Unit)?
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(obj: Any) {
                with(binding) {
                    when(obj) {
                        is PersonModel -> {
                            Picasso.with(root.context).load(obj.getProfileImageUrl()).into(itemLargeImageCircleProfilePhoto)
                            itemLargeImageCircleTitle.text = obj.name
                            itemLargeImageCircleSubtitle.text = obj.character
                        }
                        is ProductionCompanyModel -> {
                            itemLargeImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                            Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemLargeImageCircleProfilePhoto)
                            itemLargeImageCircleTitle.text = obj.name
                        }
                        is ProviderModel -> {
                            itemLargeImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                            Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemLargeImageCircleProfilePhoto)
                            itemLargeImageCircleTitle.text = obj.name
                        }
                    }
                    root.setOnClickListener {
                        clickListener?.invoke(obj)
                    }
                }
            }

            companion object {
                fun create(parent: ViewGroup, clickListener: ((obj: Any) -> Unit)?): CircleWithTitleViewHolder {
                    val binding = ItemLargeImageCircleWithTitleBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                    return CircleWithTitleViewHolder(binding, clickListener)
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