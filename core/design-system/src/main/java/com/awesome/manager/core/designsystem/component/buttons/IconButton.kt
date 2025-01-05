package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.cards.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIconButton(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType.ImageVictorAmIconsType,
    isPositive: Boolean?,
    onClick: () -> Unit
) {
    AmCard(
        modifier = modifier,
        padding = AmPadding.ZERO,
        shape = MaterialTheme.shapes.large,
        isPositive = isPositive
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
        isPositive = true,
        onClick = {}
    )
}