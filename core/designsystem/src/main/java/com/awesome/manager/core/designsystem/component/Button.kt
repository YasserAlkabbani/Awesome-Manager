package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable


@Composable
fun MaButton(title:String,onClick:()->Unit){
    Button(onClick = onClick) {
        MaText(text=title)
    }
}