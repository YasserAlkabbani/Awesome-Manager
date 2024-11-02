package com.awesome.manager.core.designsystem.component.cards

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmLinearProgress
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmItemCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium, padding: AmPadding = AmPadding.MEDIUM,
    positive: Boolean, loading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val primaryContainer = MaterialTheme.colorScheme.primary
    val errorContainer = MaterialTheme.colorScheme.error
    val cardColors = remember(positive) {
        when (positive) {
            true -> primaryContainer
            false -> errorContainer
        }
    }
    Surface(
        modifier = modifier,
        content = {
            Column{
                Column(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(padding.value),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    content()
                }
                AnimatedContent(loading, label = "CARD_INDICATOR") { loading ->
                    when (loading) {
                        true -> AmLinearProgress(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(AmSize.XXXX_SMALL.value),
                            positive = positive,
                        )

                        false -> Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(AmSize.XXXX_SMALL.value),
                            color = cardColors
                        ) {}
                    }
                }

            }
        },
        onClick = onClick,
        shape = shape
    )


}

@Preview
@Composable
fun AmCardPreview() {
    AmItemCard(
        content = {
            Column {
                AmText(text = "TEXT 1")
                AmText(text = "TEXT 2")
                AmText(text = "TEXT 3")
            }
        },
        loading = true,
        positive = true,
        onClick = {}
    )
}