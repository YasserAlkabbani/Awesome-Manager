package com.awesome.manager.core.designsystem.component.buttons

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmTextButton(
    modifier: Modifier = Modifier,
    text: String, enabled: Boolean = true, onClick: () -> Unit
) {
    TextButton(modifier = modifier, onClick = onClick, enabled = enabled) {
        AmText(text = text)
    }
}

@Preview
@Composable
fun AmTextButtonPreview() {
    AmTextButton(text = "CLICK ME !!", enabled = true, onClick = {})
}