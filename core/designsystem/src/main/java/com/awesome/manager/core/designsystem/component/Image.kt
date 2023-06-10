package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AmImage(modifier:Modifier,imageUrl:String){
    Image(
        modifier = modifier,
        imageVector = Icons.Default.Android,
        contentDescription = null
    )
}