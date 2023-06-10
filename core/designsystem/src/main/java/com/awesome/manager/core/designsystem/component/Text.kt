package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AmText(modifier: Modifier=Modifier,text:String){
    Text(modifier=modifier,text = text)
}