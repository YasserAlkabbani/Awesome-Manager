package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.AmIconsType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmAppBar(
    modifier: Modifier,
    title:String,
    navigationIcon:AmIconsType,
    actionIcon:AmIconsType,
    onNavigationClick:()->Unit,
    onActionClick:()->Unit
){

    TopAppBar(
        modifier = modifier,
        title = { AmText(text = title) },
        navigationIcon = { AmIconButton(amIconsType = navigationIcon, onClick = onNavigationClick) },
        actions = { AmIconButton(amIconsType = actionIcon, onClick = onActionClick) },
    )

}