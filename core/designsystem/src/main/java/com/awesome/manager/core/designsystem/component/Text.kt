package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun AmText(
    modifier: Modifier=Modifier,
    text:String, style:TextStyle=LocalTextStyle.current
){
    Text(
        modifier=modifier,
        text = text,
        style =style
    )
}