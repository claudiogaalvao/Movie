package com.claudiogalvaodev.moviemanager.ui.speciallist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.claudiogalvaodev.moviemanager.data.model.SpecialItem
import com.claudiogalvaodev.moviemanager.utils.enums.ItemType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class SpecialListViewModel(
    val eventId: String,
    private val firestoreDB: FirebaseFirestore
): ViewModel() {

    private val _specialItem = MutableStateFlow<List<SpecialItem>>(mutableListOf())
    val specialItem = _specialItem.asStateFlow()

    init {
        getAllItemsFromEventById()
    }

    private fun getAllItemsFromEventById() {
        try {
            firestoreDB.collection("itemsForEvents")
                .whereEqualTo("eventRef", eventId)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot != null) {
                        val allItems: MutableList<SpecialItem> = mutableListOf()
                        for (document in snapshot.documents) {
                            val currentLanguage = Locale.getDefault().toLanguageTag().replace("-", "")

                            val title = document["title.${currentLanguage}"].toString()
                            val subtitle = document["subtitle.${currentLanguage}"].toString()
                            val type = document["type"].toString()
                            val imageUrl = document["imageUrl.${currentLanguage}"].toString()
                            val categories = if(document["categories"] != null) document["categories"] as List<*> else emptyList<String>()
                            val categoriesWinner = if(document["categoriesWinner"] != null) document["categoriesWinner"] as List<*> else emptyList<String>()

                            val item = SpecialItem(
                                itemId = document["itemId"].toString().toInt(),
                                title = if (title == "null") document["title.enUS"].toString() else title,
                                subtitle = if (subtitle == "null") document["subtitle.enUS"].toString() else subtitle,
                                type = type,
                                releaseDate = document["releaseDate"].toString(),
                                imageUrl = if (imageUrl == "null") document["imageUrl.enUS"].toString() else imageUrl,
                                leastOneMovieId = if (type == ItemType.PERSON.name) document["leastOneMovieId"].toString().toInt() else 0,
                                categories = categories.map { it.toString() },
                                categoriesWinner = categoriesWinner.map { it.toString() }
                            )
                            allItems.add(item)
                        }
                        _specialItem.value = allItems
                    }
                }
        } catch (e: Exception) {
            Log.i("firestore error", "Something went wrong when try to get items from firestore")
        }
    }

}