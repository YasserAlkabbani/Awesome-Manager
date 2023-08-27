package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AmSurface(
    modifier: Modifier=Modifier, positive:Boolean?, loading:Boolean,
    onClick:()->Unit,
    content:@Composable ()->Unit
) {
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors= remember(positive) {
        when (positive) {
            null->surface
            true->primary
            false->error
        }
    }
    Surface(
        modifier=modifier,
        shape = MaterialTheme.shapes.medium,
        color=surfaceColors,
        content={
            Column {
                Column(modifier=modifier.padding(8.dp)){
                    content()
                }
                AnimatedVisibility(visible = loading) {
                    AmLinearProgress(modifier=Modifier.fillMaxWidth(),positive=positive==true)
                }
            }
        },
        onClick = onClick,enabled=!loading
    )
}

@Composable
fun AmSurface(
    modifier: Modifier=Modifier, positive:Boolean?, loading:Boolean,
    content:@Composable ()->Unit
) {
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val error = MaterialTheme.colorScheme.error
    val surfaceColors= remember(positive) {
        when (positive) {
            null->surface
            true->primary
            false->error
        }
    }
    Surface(
        modifier=modifier,
        shape = MaterialTheme.shapes.medium,
        color=surfaceColors,
        content={
            Column {
                Column(modifier=modifier.padding(8.dp)){
                    content()
                }
                AnimatedVisibility(visible = loading) {
                    AmLinearProgress(modifier=Modifier.fillMaxWidth(),positive=positive==true)
                }
            }
        },
    )

}