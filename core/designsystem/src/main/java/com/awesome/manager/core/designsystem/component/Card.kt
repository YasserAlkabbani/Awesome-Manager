package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
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
    shape: Shape=MaterialTheme.shapes.medium,
    balance:Boolean?, loading:Boolean,
    onClick:()->Unit,
    content:@Composable ColumnScope.()->Unit
){
    val primary = MaterialTheme.colorScheme.primary
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val colors: CardColors = CardDefaults.cardColors(
        containerColor = when (balance) {
            null->primary
            true->primaryContainer
            false->errorContainer
        }
    )
    Card(
        modifier = modifier,
        onClick = onClick,
        content={
            Column(modifier=modifier.padding(8.dp)){
                content()
            }
            AnimatedVisibility(visible = loading) {
                AmLinearProgress(modifier=Modifier.fillMaxWidth(),positive=balance==true)
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