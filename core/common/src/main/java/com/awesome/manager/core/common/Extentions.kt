package com.awesome.manager.core.common

import java.text.SimpleDateFormat
import java.util.Locale


fun Long.asData() = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)


//fun String.asLongDate()= Instant.parse(this).toEpochMilliseconds()

//fun Long.asStringDate()=""