package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AmCircularProgressIndicator(
    modifier: Modifier = Modifier,
) {
    LoadingIndicator(modifier = modifier)
}


@Preview
@Composable
fun AmCircularProgressPreview() {
    AmCircularProgressIndicator(Modifier)
}