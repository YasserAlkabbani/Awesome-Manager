package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmIconButton(
    modifier: Modifier,
    amIconsType: AmIconsType,
    positive: Boolean?,
    onClick: () -> Unit
) {
    AmSurface(
        modifier = modifier, positive = positive,
        highPadding = true, onClick = onClick
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
        positive = true,
        onClick = {}
    )
}