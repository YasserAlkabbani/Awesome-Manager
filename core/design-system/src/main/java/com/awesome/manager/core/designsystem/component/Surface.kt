package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
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
import com.awesome.manager.core.designsystem.AmPadding

@Composable
fun AmSurface(
    modifier: Modifier = Modifier, positive: Boolean? = null, loading: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium, padding: AmPadding = AmPadding.MEDIUM,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors = remember(positive) {
        when (positive) {
            null -> surface
            true -> primary
            false -> error
        }
    }
    when (onClick) {
        null -> {
            Surface(
                modifier = modifier,
                shape = shape,
                color = surfaceColors,
                content = {
                    Column {
                        Column(
                            modifier = Modifier.padding(padding.value),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            content()
                        }
                        AnimatedVisibility(visible = loading) {
                            AmLinearProgress(
                                modifier = Modifier.fillMaxWidth(),
                                positive = positive
                            )
                        }
                    }
                },
            )
        }

        else -> {
            Surface(
                modifier = modifier,
                shape = shape,
                color = surfaceColors,
                content = {
                    Column {
                        Column(
                            modifier = Modifier.padding(padding.value),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            content()
                        }
                        AnimatedVisibility(visible = loading) {
                            AmLinearProgress(
                                modifier = Modifier.fillMaxWidth(),
                                positive = positive
                            )
                        }
                    }
                },
                onClick = onClick, enabled = !loading
            )
        }
    }
}