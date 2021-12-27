package com.claudiogalvaodev.moviemanager.model

data class Credits(
    var cast: List<Employe>,
    var crew: List<Employe>
)