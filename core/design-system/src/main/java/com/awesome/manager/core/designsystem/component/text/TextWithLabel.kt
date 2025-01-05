package com.awesome.manager.core.designsystem.component.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.cards.AmCard
import com.awesome.manager.core.designsystem.component.surface.AmSurface

@Composable
fun AmTextWithLabel(
    modifier: Modifier = Modifier,
    label: String?, text: String?,
    positive: Boolean?, maxLines: Int = 1,
) {
    AmSurface(
        modifier = modifier,
        padding = AmPadding.MEDIUM,
        isPositive = positive,
        content = {
            Column {
                AmText(
                    modifier = Modifier.fillMaxWidth(),
                    text = label.orEmpty(),
                    style = MaterialTheme.typography.titleMedium
                )
                AmText(
                    modifier = Modifier.fillMaxWidth(),
                    text = text.orEmpty(), maxLines = maxLines,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}

@Preview
@Composable
fun AmTextWithLabelPreview() {
    AmTextWithLabel(label = "LABEL", text = "TEXT", positive = true)
}