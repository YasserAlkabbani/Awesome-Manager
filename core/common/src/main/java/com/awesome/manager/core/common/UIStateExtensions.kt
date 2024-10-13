package com.awesome.manager.core.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long.asDate() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .run { "$date" }

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

fun Long.toStringDateTime() =
    Instant.fromEpochMilliseconds(this).toString()

fun currentTime() = Clock.System.now().toEpochMilliseconds()


//fun Long.asData() = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)
//fun String.asLongDate()= Instant.parse(this).toEpochMilliseconds()