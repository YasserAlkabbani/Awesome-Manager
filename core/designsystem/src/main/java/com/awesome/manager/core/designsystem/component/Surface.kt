package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AmSurface(
    modifier: Modifier=Modifier,balance:Boolean?,
    onClick:()->Unit,
    content:@Composable ()->Unit
) {
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors= remember(balance) {
        when (balance) {
            null->surface
            true->primary
            false->error
        }
    }
    Surface(
        modifier=modifier,
        shape = MaterialTheme.shapes.small,
        content=content,
        color=surfaceColors,
        onClick = onClick
    )
}

@Composable
fun AmSurface(
    modifier: Modifier=Modifier, balance:Boolean?,
    content:@Composable ()->Unit
) {
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors= remember(balance) {
        when (balance) {
            null->surface
            true->primary
            false->error
        }
    }
    Surface(
        modifier=modifier,
        shape = MaterialTheme.shapes.small,
        color=surfaceColors,
        content=content,
    )

}