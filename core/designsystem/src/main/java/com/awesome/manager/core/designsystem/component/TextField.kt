package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun AmTextField(text:String, onTextChange:(String)->Unit){
    TextField(value = text, onValueChange = onTextChange)
}