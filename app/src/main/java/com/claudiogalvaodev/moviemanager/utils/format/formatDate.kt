package com.claudiogalvaodev.moviemanager.utils.format

import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object formatDateUtils {

    fun fromAmericanFormatToDateWithMonthName(date: String): String {
        val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val endFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val initDate = LocalDate.parse(date, initFormatter)
        return initDate.format(endFormatter)
    }

}