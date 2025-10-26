package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow

fun TextFieldState.asFlow()= snapshotFlow { text.toString() }
fun TextFieldState.asString()= this.text.toString()