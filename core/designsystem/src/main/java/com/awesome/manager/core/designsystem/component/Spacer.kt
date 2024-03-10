package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AmSpacerSmallWidth(){
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun AmSpacerMediumWidth(){
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun AmSpacerLargeWidth(){
    Spacer(modifier = Modifier.width(16.dp))
}


@Composable
fun AmSpacerSmallHight(){
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun AmSpacerMediumHight(){
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun AmSpacerLargeHight(){
    Spacer(modifier = Modifier.height(16.dp))
}