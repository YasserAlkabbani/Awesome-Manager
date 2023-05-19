package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.MaIconsType

@Composable
fun MaNavigationBar(
    modifier: Modifier,
    content:@Composable RowScope.()->Unit
){

    NavigationBar(
        modifier=modifier,
        content=content
    )

}


@Composable
fun RowScope.MaNavigationItem(
    modifier: Modifier,
    isSelected:Boolean,
    selectedIcon:MaIconsType,
    unSelectedIcon:MaIconsType,
    label:String,
    onSelect:()->Unit,
){
    NavigationBarItem(
        modifier=modifier,
        selected=isSelected,
        icon = { MaIcon(if (isSelected)selectedIcon else unSelectedIcon) },
        onClick = onSelect,
        label = { MaText(text = label)}
    )
}