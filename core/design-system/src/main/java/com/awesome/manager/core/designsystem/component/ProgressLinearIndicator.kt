package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AmLinearProgressIndicator(
    modifier: Modifier = Modifier
) {
    LinearWavyProgressIndicator(
        modifier = modifier,
    )
}


@Preview
@Composable
fun AmLinearProgressPreview() {
    AmLinearProgressIndicator()
}