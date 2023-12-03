package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    positive: Boolean?, loading: Boolean,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val primary = MaterialTheme.colorScheme.secondaryContainer
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val cardColors = remember(positive) {
        when (positive) {
            null -> primary
            true -> primaryContainer
            false -> errorContainer
        }
    }
    Card(
        modifier = modifier,
        onClick = onClick,
        content = {
            Column(modifier = modifier.padding(6.dp)) {
                content()
            }
            AnimatedVisibility(visible = loading) {
                AmLinearProgress(modifier = Modifier.fillMaxWidth(), positive = positive == true)
            }
        },
        colors = CardDefaults.cardColors(containerColor = cardColors),
        shape = shape
    )

}

@Composable
fun AmCard(
    modifier: Modifier = Modifier,
    positive: Boolean?, shape: Shape = MaterialTheme.shapes.medium,
    content: @Composable ColumnScope.() -> Unit
) {
    val primary = MaterialTheme.colorScheme.secondaryContainer
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val cardColors = remember(positive) {
        when (positive) {
            null -> primary
            true -> primaryContainer
            false -> errorContainer
        }
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = cardColors),
        content = {
            Column(modifier = modifier.padding(6.dp)) {
                content()
            }
        },
        shape = shape,
    )

}