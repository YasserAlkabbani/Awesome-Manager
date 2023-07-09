package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIcon(modifier: Modifier=Modifier,amIconsType: AmIconsType){
    when(amIconsType){
        is AmIconsType.ImageVictorAmIconsType ->
            Icon(modifier=modifier,imageVector = amIconsType.imageVector, contentDescription = null)
        is AmIconsType.DrawableResourceAmIconsType ->
            Icon(modifier=modifier,painter = painterResource(id = amIconsType.id), contentDescription = null)
        is AmIconsType.PainterAmIconsType ->
            Icon(modifier=modifier,painter = amIconsType.painter, contentDescription = null)
    }
}