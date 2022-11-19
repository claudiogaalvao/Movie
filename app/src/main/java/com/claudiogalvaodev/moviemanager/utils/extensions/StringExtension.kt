package com.claudiogalvaodev.moviemanager.utils.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toDate(): LocalDate {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(this, dateTimeFormatter)
}