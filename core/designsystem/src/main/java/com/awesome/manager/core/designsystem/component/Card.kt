package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmCard(modifier: Modifier=Modifier,onClick:()->Unit,content:@Composable ColumnScope.()->Unit){

    Card(
        modifier = modifier,
        onClick = onClick,
        content=content
    )

}

@Composable
fun AmCard(modifier: Modifier=Modifier,content:@Composable ColumnScope.()->Unit){

    Card(
        modifier = modifier,
        content=content,
    )

}