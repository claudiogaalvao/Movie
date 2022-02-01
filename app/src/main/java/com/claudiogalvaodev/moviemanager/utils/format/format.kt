package com.claudiogalvaodev.moviemanager.utils.format

import java.lang.Exception
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object formatUtils {

    fun dateFromAmericanFormatToDateWithMonthName(date: String): String? {
        try {
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
        } catch (e: Exception) {
            return null
        }
    }

    fun dateFromAmericanFormatToAge(birthday: String): String? {
        try {
            val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val birthdayDate = LocalDate.parse(birthday, initFormatter)
            val currentDate = LocalDate.now()

            var age = currentDate.year - birthdayDate.year

            if(currentDate.dayOfYear < birthdayDate.dayOfYear) age--

            return age.toString()
        } catch (e: Exception) {
            return null
        }
    }

    fun unformattedNumberToCurrency(value: Long): String {
        val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag(currentDeviceLanguage))
        numberFormat.maximumFractionDigits = 2;
        val convert = numberFormat.format(value)
        return convert ?: value.toString()
    }

}