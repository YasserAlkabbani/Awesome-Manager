package com.awesome.manager.core.designsystem.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmCard

@Composable
fun AmTextWithLabel(
    modifier: Modifier = Modifier,
    label: String?, text: String?,
    positive: Boolean?, maxLines: Int = 1,
) {
    AmCard(
        modifier = modifier, padding = AmPadding.MEDIUM,
        positive = positive
    ) {
        AmText(
            modifier = Modifier,
            text = label.orEmpty(),
            style = MaterialTheme.typography.titleMedium
        )
        AmText(
            text = text.orEmpty(), maxLines = maxLines,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun AmTextWithLabelPreview() {
    AmTextWithLabel(label = "LABEL", text = "TEXT", positive = true)
}