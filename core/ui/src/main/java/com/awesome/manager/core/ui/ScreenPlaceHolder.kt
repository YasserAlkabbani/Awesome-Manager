package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.awesome.manager.core.designsystem.component.AmCard

@Composable
fun ScreenPlaceHolder(
    modifier: Modifier=Modifier,
    title:String,
    textButton:String,
    onClickButton:()->Unit
){
    AmCard(
        modifier = Modifier,
        balance = null,
        content = {
            Column(modifier.fillMaxSize()) {
                Text(text = title, fontSize = 36.sp)
                Button(onClick = onClickButton) {
                    Text(text = textButton)
                }
            }
        }
    )
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