package com.awesome.manager.core.designsystem.component.cards

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmLinearProgress
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun ItemCard(
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
    Surface (
        modifier = modifier,
        content = {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                AnimatedContent(loading, label = "CARD_INDICATOR") { loading ->
                    when (loading) {
                        true -> AmLinearProgress(
                            modifier = Modifier
                                .width(AmSize.XXX_SMALL.value)
                                .fillMaxHeight(),
                            positive = positive,
                        )
                        false -> Surface(
                            modifier.width(AmSize.XXX_SMALL.value)
                                .fillMaxHeight(),
                            color = cardColors
                        ) {}
                    }
                }
                Column(
                    modifier = Modifier.padding(padding.value),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    content()
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
    ItemCard(
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