package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmTextManager

@Composable
fun AmExtendedFloatingActionButton(
    modifier: Modifier,
    expanded:Boolean,
    text:String,
    icon:AmIconsType,
    onClick:()->Unit
){
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        expanded = expanded,
        text = { AmText(modifier=Modifier,text = text)},
        icon = { AmIcon(amIconsType = icon)} ,
        elevation = FloatingActionButtonDefaults.elevation(0.dp)
    )
}