package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun AmSurface(
    modifier: Modifier = Modifier, positive: Boolean?, loading: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium, onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val surface = MaterialTheme.colorScheme.secondary
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors = remember(positive) {
        when (positive) {
            null -> surface
            true -> primary
            false -> error
        }
    }
    Surface(
        modifier = modifier,
        shape = shape,
        color = surfaceColors,
        content = {
            Column {
                Column(
                    modifier = modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }
                AnimatedVisibility(visible = loading) {
                    AmLinearProgress(
                        modifier = Modifier.fillMaxWidth(),
                        positive = positive == true
                    )
                }
            }
        },
        onClick = onClick, enabled = !loading
    )
}

@Composable
fun AmSurface(
    modifier: Modifier = Modifier, positive: Boolean? = null, loading: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium, highPadding:Boolean,
    content: @Composable () -> Unit
) {
    val surface = MaterialTheme.colorScheme.secondary
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors = remember(positive) {
        when (positive) {
            null -> surface
            true -> primary
            false -> error
        }
    }
    val padding= remember(highPadding) {
        if (highPadding) 6.dp else 3.dp
    }
    Surface(
        modifier = modifier,
        shape = shape,
        color = surfaceColors,
        content = {
            Column {
                Column(modifier = modifier.padding(padding)) {
                    content()
                }
                AnimatedVisibility(visible = loading) {
                    AmLinearProgress(
                        modifier = Modifier.fillMaxWidth(),
                        positive = positive == true
                    )
                }
            }
        },
    )

}