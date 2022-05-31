package com.claudiogalvaodev.moviemanager.ui.model

data class BottomSheetOfListsUI(
    val id: String,
    val name: String,
    val isSaved: Boolean,
    val saveOn: SaveOn
)

enum class SaveOn {
    USER_LIST, SPECIAL_LIST
}