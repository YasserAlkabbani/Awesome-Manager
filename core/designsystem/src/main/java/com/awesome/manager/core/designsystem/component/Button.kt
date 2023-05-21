package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.text.AmText


@Composable
fun AmButton(amText: AmText, onClick:()->Unit){
    Button(onClick = onClick) {
        AmText(amText=amText)
    }
}