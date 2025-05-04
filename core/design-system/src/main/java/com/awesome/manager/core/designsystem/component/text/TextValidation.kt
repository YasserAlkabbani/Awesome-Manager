package com.awesome.manager.core.designsystem.component.text

import androidx.compose.foundation.text.input.TextFieldState

fun TextFieldState.isValidAccountName() = text.isNotEmpty()