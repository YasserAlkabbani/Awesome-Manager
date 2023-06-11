package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmAppBar(
    modifier: Modifier,
    title:String,
    onNavigationBack:()->Unit,
    onEdit:(()->Unit)?,
){
    TopAppBar(
        modifier = modifier,
        title = { AmText(text = title) },
        navigationIcon = {
            AmIconButton(amIconsType = AmIcons.ArrowBack, onClick = onNavigationBack)
        },
        actions = {
            onEdit?.let {
                AmIconButton(amIconsType = AmIcons.Edit, onClick = onEdit)
            }
        },
    )

}