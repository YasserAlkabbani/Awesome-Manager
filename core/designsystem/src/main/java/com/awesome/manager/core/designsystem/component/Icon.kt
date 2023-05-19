package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.awesome.manager.core.designsystem.icon.MaIcons
import com.awesome.manager.core.designsystem.icon.MaIconsType

@Composable
fun MaIcon(maIconsType: MaIconsType){
    when(maIconsType){
        is MaIconsType.ImageVictorMaIconsType -> Icon(imageVector = maIconsType.imageVector, contentDescription = null)
        is MaIconsType.DrawableResourceMaIconsType -> Icon(painter = painterResource(id = maIconsType.id), contentDescription = null)
        is MaIconsType.PainterMaIconsType -> Icon(painter = maIconsType.painter, contentDescription = null)
    }
}