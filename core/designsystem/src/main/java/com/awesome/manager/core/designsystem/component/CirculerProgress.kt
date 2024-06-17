package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmCircularProgress(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(modifier = modifier)
}


@Preview
@Composable
fun AmCircularProgressPreview() {
    AmCircularProgress(Modifier)
}