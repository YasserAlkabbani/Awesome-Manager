package com.awesome.manager.core.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmText
import com.awesome.manager.core.designsystem.text.asAmText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmAppBar(
    modifier: Modifier,
    title:AmText,
    navigationIcon:AmIconsType,
    actionIcon:AmIconsType,
    onNavigationClick:()->Unit,
    onActionClick:()->Unit
){

    TopAppBar(
        modifier = modifier,
        title = { AmText(amText = title) },
        navigationIcon = { AmIconButton(amIconsType = navigationIcon, onClick = onNavigationClick) },
        actions = { AmIconButton(amIconsType = actionIcon, onClick = onActionClick) },
    )

}