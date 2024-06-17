package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIconButton(
    modifier: Modifier = Modifier, positive: Boolean? = null,
    amIconsType: AmIconsType, onClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val color = remember(positive) {
        when (positive) {
            true -> primary
            false -> error
            null -> null
        }
    } ?: IconButtonDefaults.iconButtonColors().containerColor
    FilledIconButton(
        modifier = modifier, onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = color,
        )
    ) {
        AmIcon(amIconsType = amIconsType)
    }
}

@Preview
@Composable
fun AmIconButtonPreview() {
    AmIconButton(
        modifier = Modifier,
        amIconsType = AmIcons.ArrowBack,
        onClick = {}
    )
}