package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.text.AmTextManager


@Composable
fun AmButton(modifier: Modifier=Modifier,text:String, onClick:()->Unit){
    Button(modifier=modifier,onClick = onClick) {
        AmText(text = text)
    }
}