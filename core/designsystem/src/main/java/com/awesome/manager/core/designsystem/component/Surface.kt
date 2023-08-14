package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AmSurface(
    modifier: Modifier,
    onClick:()->Unit,
    content:@Composable ()->Unit
) {
    Surface(
        modifier=modifier,
        shape = MaterialTheme.shapes.small,
        content=content,
        onClick = onClick
    )
}

@Composable
fun AmSurface(
    modifier: Modifier,
    content:@Composable ()->Unit
) {

    Surface(
        modifier=modifier,
        shape = MaterialTheme.shapes.small,
        content=content,
    )

}