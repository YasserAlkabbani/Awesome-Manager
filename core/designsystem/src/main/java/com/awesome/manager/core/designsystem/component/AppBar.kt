package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.AmTextManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmAppBar(
    modifier: Modifier,
    title: String, onBack: () -> Unit,
    onSave: () -> Unit, showSave: Boolean,
    onEdit: () -> Unit, showEdit: Boolean,
) {
    TopAppBar(
        modifier = modifier,
        title = { AmText(text = title) },
        navigationIcon = {
            AmIconButton(amIconsType = AmIcons.NavigationBack, onClick = onBack)
        },
        actions = {
            AnimatedVisibility(visible = showEdit) {
                AmIconButton(amIconsType = AmIcons.Save, onClick = onEdit)
            }
            AnimatedVisibility(visible = showSave) {
                AmIconButton(amIconsType = AmIcons.Edit, onClick = onSave)
            }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmDetailsAppBar(
    modifier: Modifier = Modifier,
    title: AmTextManager,
    onNavigationBack: (() -> Unit)?,
    onSave: (() -> Unit)?,
    onEdit: (() -> Unit)?
) {
    TopAppBar(
        modifier = modifier.wrapContentHeight(),
        title = { AmText(text = title.asText()) },
        navigationIcon = {
            AnimatedVisibility(visible = onNavigationBack != null) {
                AmIconButton(
                    amIconsType = AmIcons.NavigationBack,
                    onClick = { onNavigationBack?.invoke() })
            }
        },
        actions = {
            AnimatedVisibility(visible = onSave != null) {
                AmIconButton(amIconsType = AmIcons.Save, onClick = { onEdit?.invoke() })
            }
            AnimatedVisibility(visible = onEdit != null) {
                AmIconButton(amIconsType = AmIcons.Edit, onClick = { onSave?.invoke() })
            }
        },
    )
}