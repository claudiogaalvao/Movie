package com.claudiogalvaodev.moviemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.databinding.ItemOptionsMyListsBinding

class MyListsAdapter(
    private val moviesSaved: List<MovieSaved>? = null
): ListAdapter<MyList, MyListsAdapter.MyListsViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((listSelected: MyList, action: Action) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListsViewHolder {
        // verificar se o filme está na lsita de filmes salvos
        // passar a lista do find para o viewholder
        return MyListsViewHolder.create(parent, moviesSaved, onItemClick)
    }

    override fun onBindViewHolder(holder: MyListsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyListsViewHolder(
        private val binding: ItemOptionsMyListsBinding,
        private val moviesSaved: List<MovieSaved>? = null,
        private val clickListener: ((listSelected: MyList, action: Action) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyList) {
            binding.itemOptionsMyListsName.text = item.name

            val isSavedInThisList = moviesSaved?.find { movie -> movie.myListId == item.id } != null

            if(isSavedInThisList) {
                binding.itemOptionsMyListsSavedMark.visibility = View.VISIBLE
            }

            val action = if(isSavedInThisList) Action.REMOVE else Action.INSERT

            binding.root.setOnClickListener {
                clickListener?.invoke(item, action)
            }

        }

        companion object {
            fun create(
                parent: ViewGroup,
                moviesSaved: List<MovieSaved>? = null,
                clickListener: ((listSelected: MyList, action: Action) -> Unit)?
            ): MyListsViewHolder {
                val binding = ItemOptionsMyListsBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return MyListsViewHolder(binding, moviesSaved, clickListener)
            }
        }

    }

    companion object {
        enum class Action {
            INSERT, REMOVE
        }

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