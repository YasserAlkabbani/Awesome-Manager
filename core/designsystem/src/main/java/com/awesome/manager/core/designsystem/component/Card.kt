package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

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
        content={
            Column(modifier=modifier.padding(8.dp)){
                content()
            }
        },
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
        content={
            Column(modifier=modifier.padding(8.dp)){
                content()
            }
        },
        shape = shape,
    )

}