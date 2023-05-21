package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIcon(amIconsType: AmIconsType){
    when(amIconsType){
        is AmIconsType.ImageVictorAmIconsType -> Icon(imageVector = amIconsType.imageVector, contentDescription = null)
        is AmIconsType.DrawableResourceAmIconsType -> Icon(painter = painterResource(id = amIconsType.id), contentDescription = null)
        is AmIconsType.PainterAmIconsType -> Icon(painter = amIconsType.painter, contentDescription = null)
    }
}