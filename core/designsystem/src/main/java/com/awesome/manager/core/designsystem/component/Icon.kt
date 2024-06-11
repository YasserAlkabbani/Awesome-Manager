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
    amIconsType: AmIconsType,
    positive: Boolean? = null
) {
    val default = LocalContentColor.current
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val tint = remember(positive) {
        when (positive) {
            null -> default
            true -> primary
            false -> error
        }
    }
    when (amIconsType) {
        is AmIconsType.ImageVictorAmIconsType ->
            Icon(
                modifier = modifier, imageVector = amIconsType.imageVector,
                tint = tint, contentDescription = null
            )

        is AmIconsType.DrawableResourceAmIconsType ->
            Icon(
                modifier = modifier, painter = painterResource(id = amIconsType.id),
                tint = tint, contentDescription = null
            )

        is AmIconsType.PainterAmIconsType ->
            Icon(
                modifier = modifier, painter = amIconsType.painter,
                tint = tint, contentDescription = null
            )
    }
}