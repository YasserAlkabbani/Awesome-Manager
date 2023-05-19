package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.MaIconsType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaAppBar(
    modifier: Modifier,
    title:String,
    navigationIcon:MaIconsType,
    actionIcon:MaIconsType,
    onNavigationClick:()->Unit,
    onActionClick:()->Unit
){

    TopAppBar(
        modifier = modifier,
        title = { MaText(text = title) },
        navigationIcon = { MaIconButton(maIconsType = navigationIcon, onClick = onNavigationClick) },
        actions = { MaIconButton(maIconsType = actionIcon, onClick = onActionClick) },
    )

}