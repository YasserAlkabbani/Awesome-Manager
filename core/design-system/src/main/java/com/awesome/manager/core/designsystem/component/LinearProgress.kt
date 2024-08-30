package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmLinearProgress(
    modifier: Modifier = Modifier,
    positive: Boolean? = null
) {

    val primary = MaterialTheme.colorScheme.primary
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val error = MaterialTheme.colorScheme.error
    val errorContainer = MaterialTheme.colorScheme.errorContainer

    val (color, trackColor) = remember(positive) {
        when (positive) {
            true -> primary to primaryContainer
            false -> error to errorContainer
            null -> null
        }
    } ?: Pair(ProgressIndicatorDefaults.linearColor, ProgressIndicatorDefaults.linearTrackColor)
    LinearProgressIndicator(modifier = modifier, color = color, trackColor = trackColor)
}


@Preview
@Composable
fun AmLinearProgressPreview() {
    AmLinearProgress()
}