package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmTextManager

@Composable
fun AmNavigationBar(
    modifier: Modifier,
    content:@Composable RowScope.()->Unit
){

    NavigationBar(
        modifier=modifier,
        content=content
    )

}


@Composable
fun RowScope.AmNavigationItem(
    modifier: Modifier,
    isSelected:Boolean,
    selectedIcon:AmIconsType,
    unSelectedIcon:AmIconsType,
    label:String,
    onSelect:()->Unit,
){
    NavigationBarItem(
        modifier=modifier,
        selected=isSelected,
        icon = { AmIcon(amIconsType = if (isSelected)selectedIcon else unSelectedIcon) },
        onClick = onSelect,
        label = { AmText(text = label)}
    )
}