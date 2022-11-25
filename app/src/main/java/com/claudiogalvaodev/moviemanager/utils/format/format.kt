package com.claudiogalvaodev.moviemanager.utils.format

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.Exception

private const val DATE_TIME_PATTERN = "yyyy-MM-dd"

object FormatUtils {

    fun String.convertToDate(): LocalDate {
        val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        return LocalDate.parse(this, initFormatter)
    }

    fun dateFromAmericanFormatToDateWithMonthName(date: String): String? {
        try {
            val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
            val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
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

    fun dateFromAmericanFormatToDateWithDayAndMonthName(date: String): String? {
        try {
            val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
            val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
            val initDate = LocalDate.parse(date, initFormatter)

            val endDate = when(currentDeviceLanguage) {
                "pt-BR" -> {
                    val brFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM")
                    initDate.format(brFormatter)
                }
                else -> {
                    val usFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd")
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
            val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
            val birthdayDate = LocalDate.parse(birthday, initFormatter)
            val currentDate = LocalDate.now()

            var age = currentDate.year - birthdayDate.year

            if(currentDate.dayOfYear < birthdayDate.dayOfYear) age--

            return age.toString()
        } catch (e: Exception) {
            return null
        }
    }

    fun isFutureDate(date: String): Boolean {
        return try {
            val initFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
            val dateFormatter = LocalDate.parse(date, initFormatter)
            val currentDate = LocalDate.now()

            val diffInDays = dateFormatter.dayOfYear.minus(currentDate.dayOfYear)

            diffInDays > 0
        } catch (e: Exception) {
            false
        }
    }

    fun unformattedNumberToCurrency(value: Long): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-US"))
        numberFormat.maximumFractionDigits = 2
        val convert = numberFormat.format(value)
        return convert ?: value.toString()
    }

}