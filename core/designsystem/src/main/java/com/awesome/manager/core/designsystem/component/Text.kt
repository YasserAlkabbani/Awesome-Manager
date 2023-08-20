package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun AmText(
    modifier: Modifier=Modifier,
    text:String,maxLines:Int = 1,
    style:TextStyle=LocalTextStyle.current
){
    Text(
        modifier=modifier,
        text = text,
        maxLines = maxLines,
        style =style
    )
}