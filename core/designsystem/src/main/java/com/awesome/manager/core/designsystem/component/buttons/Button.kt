package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmText


@Composable
fun AmButton(modifier: Modifier=Modifier,text:String, onClick:()->Unit){
    Button(modifier=modifier,onClick = onClick) {
        AmText(text = text)
    }
}

@Preview
@Composable
fun AmButtonPreview(){
    AmButton(
        text = "CLICK ME !!",
        onClick = {}
    )
}