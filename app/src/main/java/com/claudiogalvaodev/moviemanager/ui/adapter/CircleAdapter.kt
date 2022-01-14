package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.ItemSmallImageCircleBinding
import com.claudiogalvaodev.moviemanager.model.Company
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Provider
import com.squareup.picasso.Picasso

class CircleAdapter(
    private val objList: List<Any>
): RecyclerView.Adapter<CircleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSmallImageCircleBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objSelected = objList[position]
        holder.bind(objSelected)
    }

    override fun getItemCount(): Int {
        return objList.size
    }

    inner class ViewHolder(private val binding: ItemSmallImageCircleBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            with(binding) {
                when(obj) {
                    is Employe -> {
                        Picasso.with(root.context).load(obj.getProfileImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                    is Company -> {
                        itemImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                        Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                    is Provider -> {
                        itemImageCircleProfilePhoto.scaleType = ImageView.ScaleType.FIT_CENTER
                        Picasso.with(root.context).load(obj.getLogoImageUrl()).into(itemImageCircleProfilePhoto)
                    }
                }

            }
        }
    }
}