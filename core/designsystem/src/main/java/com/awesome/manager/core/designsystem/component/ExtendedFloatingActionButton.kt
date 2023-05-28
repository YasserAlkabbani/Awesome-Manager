package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmText

@Composable
fun AmExtendedFloatingActionButton(
  modifier: Modifier,
  expanded:Boolean,
  text:AmText,
  icon:AmIconsType,
  onClick:()->Unit
){
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        expanded = expanded,
        text = { AmText(amText = text)},
        icon = { AmIcon(amIconsType = icon)} ,
    )
}