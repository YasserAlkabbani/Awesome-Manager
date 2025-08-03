@file:OptIn(ExperimentalTime::class)

package com.awesome.manager.core.common

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

val formatDateTime = LocalDateTime.Format {
    date(LocalDate.Formats.ISO)
    char(' ')
    char(' ')
    char(' ')
    hour()
    char(':')
    minute()
}

fun Long.asDate() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .format(formatDateTime)

fun Long.asShortDate() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .run {
            val (year, date, time) = date.toString().split("-")
            "$date.$time.${year.substringAfter("20")}"
        }

fun Pair<Long, Long>.asDateRange(): String {
    val (from, to) = this
    return "${from.asShortDate()} - ${to.asShortDate()}"
}

fun Long.asDateTimeString() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC).toString()

fun String?.asTimestamp() =
    Instant.parse(this.orEmpty()).toEpochMilliseconds()

fun Long.asStringDateTime() =
    Instant.fromEpochMilliseconds(this).toString()

fun currentTime() = Clock.System.now().toEpochMilliseconds()

fun String.asFormattedNumber() = filter { it.isDigit() || it == '.' }
    .removePrefix("0")
    .let {
        val fullNumber = it.split(".")
        val formattedNumber = fullNumber
            .getOrNull(0)
            .let { it.orEmpty().ifBlank { "0" } }
            .take(16)
        val formattedDecimalNumber = fullNumber
            .getOrNull(1)
            .let { it.orEmpty().ifBlank { "0" } }
            .take(3)
        "$formattedNumber.$formattedDecimalNumber"
    }

//fun Long.asData() = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)
//fun String.asLongDate()= Instant.parse(this).toEpochMilliseconds()