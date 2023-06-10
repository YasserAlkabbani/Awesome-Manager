package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.text.AmTextManager

@Composable
fun AmTextButton(modifier:Modifier=Modifier,text:String, onClick:()->Unit){
    TextButton(modifier=modifier,onClick = onClick) {
        AmText(text=text)
    }
}