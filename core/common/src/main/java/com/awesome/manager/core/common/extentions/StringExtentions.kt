package com.awesome.manager.core.common.extentions

fun String.isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword() = this.length > 5

fun String.limitName()=this.substringBefore(" ").take(7)