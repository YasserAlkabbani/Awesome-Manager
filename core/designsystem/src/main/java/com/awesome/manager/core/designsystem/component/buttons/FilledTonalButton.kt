package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmText


@Composable
fun AmFilledTonalButton(
    modifier: Modifier=Modifier,
    text:String, positive:Boolean?,
    onClick:()->Unit
){
    AmCard(
        modifier=modifier,
        onClick = onClick,
        positive = positive,
        loading = false
    ) {
        AmText(text = text)
    }
}

@Preview
@Composable
fun AmFilledTonalButtonPreview(){
    AmFilledTonalButton(
        text = "CLICK ME !!", positive = null,
        onClick = {}
    )
}