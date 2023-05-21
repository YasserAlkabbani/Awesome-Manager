package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.text.AmText

@Composable
fun AmTextButton(amText: AmText,onClick:()->Unit){
    TextButton(onClick = onClick) {
        AmText(amText = amText)
    }
}