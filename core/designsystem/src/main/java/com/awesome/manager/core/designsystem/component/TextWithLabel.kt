package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AmTextWithLabel(
    modifier: Modifier = Modifier,
    label: String?, text: String?, maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    positive: Boolean?
) {
    AmSurface(modifier = modifier, positive = positive, highPadding = true) {
        AmText(
            modifier = Modifier.padding(4.dp),
            text = label.orEmpty(),
            style = MaterialTheme.typography.titleMedium
        )
        AmCard(modifier = Modifier.fillMaxWidth(), positive = positive) {
            AmText(
                modifier = modifier,
                text = text.orEmpty(),
                maxLines = maxLines,
                style = style,
                textAlign = textAlign
            )
        }
    }
}

@Preview
@Composable
fun AmTextWithLabelPreview() {
    AmTextWithLabel(label = "LABEL", text = "TEXT", positive = false)
}