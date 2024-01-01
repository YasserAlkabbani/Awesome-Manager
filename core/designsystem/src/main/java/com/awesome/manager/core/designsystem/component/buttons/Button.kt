package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmButton(
    modifier: Modifier = Modifier,
    text: String, positive: Boolean?,
    onClick: () -> Unit
) {
    AmSurface(
        modifier = modifier, onClick = onClick,
        positive = positive, loading = false, highPadding = true
    ) {
        AmText(text = text)
    }
}

@Preview
@Composable
fun AmButtonPreview() {
    AmButton(
        text = "CLICK ME !!",
        onClick = {}, positive = null
    )
}