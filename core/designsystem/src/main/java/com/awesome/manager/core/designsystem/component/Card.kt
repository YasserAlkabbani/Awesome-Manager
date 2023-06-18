package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmCard(
    modifier: Modifier=Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    shape: Shape=MaterialTheme.shapes.medium,
    onClick:()->Unit,
    content:@Composable ColumnScope.()->Unit
){

    Card(
        modifier = modifier,
        onClick = onClick,
        content=content,
        colors =colors,
        shape = shape
    )

}

@Composable
fun AmCard(
    modifier: Modifier=Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    shape: Shape=MaterialTheme.shapes.medium,
    content:@Composable ColumnScope.()->Unit
){

    Card(
        modifier = modifier,
        colors=colors,
        content=content,
        shape = shape,
    )

}