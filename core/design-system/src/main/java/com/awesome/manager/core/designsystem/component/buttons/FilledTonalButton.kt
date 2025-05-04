package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText


@Composable
fun AmFilledTonalButton(
    modifier: Modifier = Modifier,
    text: String, onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        content = {
            AmText(
                modifier = Modifier.padding(horizontal = AmPadding.SMALL.value),
                textStyle = MaterialTheme.typography.titleMedium,
                text = text,
            )
        }
    )
}

@Preview
@Composable
fun AmFilledTonalButtonPreview() {
    AmFilledTonalButton(
        text = "CLICK ME !!",
        onClick = {}
    )
}