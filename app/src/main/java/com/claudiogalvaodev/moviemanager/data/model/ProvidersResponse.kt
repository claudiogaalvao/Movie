package com.claudiogalvaodev.moviemanager.data.model

data class ProvidersResponse(
    val id: Long,
    val results: HashMap<String, ProvidersOptions>
)

data class ProvidersOptions(
    val link: String,
    val flatrate: List<Provider>?,
    val rent: List<Provider>?,
    val buy: List<Provider>?
)