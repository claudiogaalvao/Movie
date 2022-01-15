package com.claudiogalvaodev.moviemanager.utils.format

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object formatUtils {

    fun dateFromAmericanFormatToDateWithMonthName(date: String): String {
        val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
        val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val initDate = LocalDate.parse(date, initFormatter)

        val endDate = when(currentDeviceLanguage) {
            "pt-BR" -> {
                val brFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
                initDate.format(brFormatter)
            }
            else -> {
                val usFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd")
                initDate.format(usFormatter)
            }
        }

        return endDate
    }

    fun unformattedNumberToCurrency(value: Long): String {
        val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag(currentDeviceLanguage))
        numberFormat.maximumFractionDigits = 2;
        val convert = numberFormat.format(value)
        return convert ?: value.toString()
    }

}