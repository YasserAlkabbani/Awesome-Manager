package com.awesome.manager.core.common.extentions

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long.asDate() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .run { "$date $time" }

fun Long.asDateTime() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)





//fun Long.asData() = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)
//fun String.asLongDate()= Instant.parse(this).toEpochMilliseconds()