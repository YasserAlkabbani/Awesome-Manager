package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIconButton(amIconsType: AmIconsType, onClick:()->Unit){
    IconButton(onClick = onClick) {
        AmIcon(amIconsType = amIconsType)
    }
}

@Preview
@Composable
fun AmIconButtonPreview(){
    AmIconButton(
        amIconsType = AmIcons.ArrowBack,
        onClick = {}
    )
}