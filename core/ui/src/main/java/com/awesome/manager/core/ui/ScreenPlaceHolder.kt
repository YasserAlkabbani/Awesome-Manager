package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ScreenPlaceHolder(
    title:String,
    textButton:String,
    onClickButton:()->Unit
){
    Column(Modifier.fillMaxSize()) {
        Text(text = title, fontSize = 36.sp)
        Button(onClick = onClickButton) {
            Text(text = textButton)
        }
    }
}


@Preview
@Composable
fun ScreenPlaceHolderPreview(){
    ScreenPlaceHolder(
        title="TEST",
        textButton = "CLICK_ME",
        onClickButton = {}
    )
}