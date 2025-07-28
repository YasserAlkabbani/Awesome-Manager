package com.awesome.manager.core.designsystem.component.surface

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmCircularProgressIndicator
import com.awesome.manager.core.designsystem.component.AmLinearProgressIndicator
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.getColors

@Composable
fun AmSurface(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: AmPadding = AmPadding.CARD_PADDING_MEDIUM,
    isPositive: Boolean? = null,
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max)
            .clip(shape)
    ) {
        Surface(
            modifier = Modifier.padding(
                bottom = AmPadding.CARD_SURFACE_INDICATOR.value
            ),
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
            }
        )
    }
}

@Composable
fun AmSurface(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    padding: AmPadding = AmPadding.CARD_PADDING_MEDIUM,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.padding(
            start = AmPadding.CARD_SURFACE_INDICATOR.value,
        ),
        shape = shape,
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier.padding(
                    vertical = padding.value + 4.dp,
                    horizontal = padding.value
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
                AnimatedVisibility(isLoading) {
                    AmCircularProgressIndicator(
                        modifier = Modifier.size(AmSize.CARD_LOADING_SIZE.value),
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AmSurfacePreview() {
    AmSurface(
        content = {
            Column {
                AmText(text = "TEXT 1")
                AmText(text = "TEXT 2")
                AmText(text = "TEXT 3")
            }
        },
        isLoading = false,
        isPositive = true
    )
}