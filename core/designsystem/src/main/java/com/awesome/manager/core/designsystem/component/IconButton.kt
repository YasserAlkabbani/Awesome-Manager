package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.icon.MaIconsType

@Composable
fun MaIconButton(maIconsType: MaIconsType, onClick:()->Unit){
    IconButton(onClick = onClick) {
        MaIcon(maIconsType = maIconsType)
    }
}