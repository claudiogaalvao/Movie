package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel

@Entity
data class CustomListEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val name: String,
    val posterPath: String
)

fun CustomListEntity.toModel() = CustomListModel(
    id = this.id,
    name = this.name,
    posterPath = this.posterPath,
    movies = emptyList()
)

fun List<CustomListEntity>.toListOfCustomListModel() = this.map {
    it.toModel()
}