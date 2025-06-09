package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmLinearProgressIndicator(
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        modifier = modifier,
    )
}


@Preview
@Composable
fun AmLinearProgressPreview() {
    AmLinearProgressIndicator()
}