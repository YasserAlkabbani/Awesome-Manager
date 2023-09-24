package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconButton
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.text.AmTextManager

data class AppBarData(
    val title: String,
    val startIcon: Pair<AmIconsType, () -> Unit>?,
    val endIcon: Pair<AmIconsType, () -> Unit>?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmAppBar(
    modifier: Modifier,
    appBarData: AppBarData
) {
    TopAppBar(
        modifier = modifier,
        title = {
            AmText(
                modifier = Modifier,
                text = appBarData.title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            appBarData.startIcon?.let {
                AmFilledTonalIconButton(
                    amIconsType = it.first,
                    positive = false,
                    onClick = it.second
                )
            }
        },
        actions = {
            appBarData.endIcon?.let {
                AmFilledTonalIconButton(
                    amIconsType = it.first,
                    positive = true,
                    onClick = it.second
                )
            }
        },
    )
}

@Preview
@Composable
fun CustomAppBarPreview() {
    AmAppBar(
        modifier = Modifier,
        appBarData = AppBarData(
            "TEST TITLE",
            AmIcons.ArrowBack to {},
            AmIcons.Save to {},
        )
    )
}