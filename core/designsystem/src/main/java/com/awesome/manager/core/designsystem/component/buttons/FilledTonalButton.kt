package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.text.AmText


@Composable
fun AmFilledTonalButton(
    modifier: Modifier = Modifier,
    text: String, positive: Boolean?,
    onClick: () -> Unit
) {
    AmCard(
        modifier = modifier,
        onClick = onClick,
        positive = positive,
        loading = false,
    ) {
        AmText(
            modifier = Modifier.padding(horizontal = AmPadding.SMALL.value), text = text
        )
    }
}

@Preview
@Composable
fun AmFilledTonalButtonPreview() {
    AmFilledTonalButton(
        text = "CLICK ME !!", positive = null,
        onClick = {}
    )
}