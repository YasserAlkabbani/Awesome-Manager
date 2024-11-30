package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIcon(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType.ImageVictorAmIconsType
) {
    Icon(
        modifier = modifier, imageVector = amIconsType.imageVector,
        contentDescription = null
    )
}
@Composable
fun AmIcon(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType.DrawableResourceAmIconsType
) {
    Icon(
        modifier = modifier, painter = painterResource(id = amIconsType.id),
        contentDescription = null
    )
}
@Composable
fun AmIcon(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType.PainterAmIconsType
) {
    Icon(
        modifier = modifier, painter = amIconsType.painter,
        contentDescription = null
    )
}