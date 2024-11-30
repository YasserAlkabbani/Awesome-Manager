package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIconButton(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType.ImageVictorAmIconsType,
    onClick: () -> Unit
) {
    AmCard(
        modifier=modifier,
        padding = AmPadding.ZERO,
        shape = MaterialTheme.shapes.large
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onClick,
        ) {
            AmIcon(
                modifier = Modifier,
                amIconsType = amIconsType
            )
        }
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