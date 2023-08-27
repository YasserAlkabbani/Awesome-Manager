package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmCircularProgress(
    modifier: Modifier=Modifier,
    positive:Boolean
){

    val primary = MaterialTheme.colorScheme.primary
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val error = MaterialTheme.colorScheme.error
    val errorContainer = MaterialTheme.colorScheme.errorContainer

    val (color,trackColor)= remember (positive){
        if(positive) primary to primaryContainer else error to errorContainer
    }

    CircularProgressIndicator(modifier=modifier, color = color,trackColor=trackColor)
}


@Preview
@Composable
fun AmCircularProgressPreview(){
    AmLinearProgress(positive = false)
}