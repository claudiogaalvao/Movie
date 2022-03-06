package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import com.claudiogalvaodev.moviemanager.utils.enums.OscarNominationType

@Entity
data class OscarNomination(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val itemId: Int,
    val type: OscarNominationType,
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String,
    val releaseDate: String? = null,
    val leastOneMovieId: Int? = null,
    val categories: List<OscarCategory>,
    val categoriesWinner: List<OscarCategory> = emptyList(),
)