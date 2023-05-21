package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIconButton(amIconsType: AmIconsType, onClick:()->Unit){
    IconButton(onClick = onClick) {
        AmIcon(amIconsType = amIconsType)
    }
}