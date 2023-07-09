package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmFilledTonalButton(
    modifier: Modifier=Modifier,
    text:String,
    onClick:()->Unit
){
    FilledTonalButton(
        modifier=modifier,

        onClick = onClick
    ) {
        AmText(text = text)
    }
}

@Preview
@Composable
fun AmFilledTonalButtonPreview(){
    AmFilledTonalButton(
        text = "CLICK ME !!",
        onClick = {}
    )
}