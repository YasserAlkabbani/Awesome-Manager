package com.awesome.manager.core.designsystem.component.text

fun String.isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidPassword() = length > 5