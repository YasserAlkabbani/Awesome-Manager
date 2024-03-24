package com.awesome.manager.core.common.extentions

fun String.isValiedEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValiedPassword() = this.length > 5