package com.awesome.manager.core.designsystem.component.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9_PRO_XL
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmLinearProgressIndicator
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: AmPadding = AmPadding.CARD_PADDING_MEDIUM,
    isPositive: Boolean? = null,
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val cardColors = remember(isPositive) {
        when (isPositive) {
            null -> secondaryContainer
            true -> primaryContainer
            false -> errorContainer
        }
    }
    Box(
        modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max)
            .clip(shape)
    ) {
        Card(
            modifier = Modifier.padding(bottom = AmPadding.CARD_SURFACE_INDICATOR.value),
            colors = CardDefaults.cardColors(containerColor = cardColors),
            shape = shape,
            content = {
                Column(
                    modifier = Modifier.padding(padding.value),
                    content = {
                        content()
                        AnimatedVisibility(isLoading) {
                            AmLinearProgressIndicator()
                        }
                    }
                )
            },
        )
    }
}

@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: AmPadding = AmPadding.CARD_PADDING_MEDIUM,
    isPositive: Boolean? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = when (isPositive) {
                    null -> MaterialTheme.colorScheme.secondaryContainer
                    true -> MaterialTheme.colorScheme.primaryContainer
                    false -> MaterialTheme.colorScheme.errorContainer
                }
        ),
        shape = shape,
        onClick = onClick,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(padding.value),
                    verticalArrangement = Arrangement.Bottom,
                    content = {
                        content()
                        AnimatedVisibility(isLoading) {
                            AmLinearProgressIndicator(modifier = Modifier.size(AmSize.CARD_LOADING_SIZE.value))
                        }
                    }
                )
            }
        }
    )
}

@Preview(device = PIXEL_9_PRO_XL, showBackground = true)
@Composable
fun AmCardPreview() {
    AmCard(
        content = {
            Column {
                AmText(text = "TEXT 1")
                AmText(text = "TEXT 2")
                AmText(text = "TEXT 3")
            }
        },
        isLoading = true,
        isPositive = false,
        onClick = {}
    )
}