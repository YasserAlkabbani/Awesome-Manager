package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.ui_actions.appbar.AppBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmAppBar(
    modifier: Modifier,
    appBarAction: AppBarAction,
    onClickProfile: () -> Unit,
    onSearchKeyChange: (String) -> Unit
) {
    when (appBarAction) {
        AppBarAction.Idle -> {}
        is AppBarAction.Search -> {
            AmSearch(
                modifier = modifier,
                searchLabel = "Search For .. ",
                syncSearchKey = appBarAction.syncSearchKey,
                errorMessage = null,
                showProfileBottomSheet = onClickProfile
            )
//            Text(modifier=modifier,text = "APPBAR")
//            TopAppBar(
//                modifier = modifier,
//                title = {
//                    AmTextField(
//                    hint = "Search For ..",
//                    icon = AmIcons.Search,
//                    label = "LABEL",
//                    error = "ERROR",
//                    onTextChange = {appBarAction.syncSearchKey(it)},
//                )
//                        Text(text = "APPBAR TEXT")
//                },
//                navigationIcon = {},
//                actions = {},
//            )
        }

        is AppBarAction.Create -> {
            TopAppBar(
                modifier = modifier,
                title = {
                    AmText(
                        modifier = Modifier,
                        text = appBarAction.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    AmFilledTonalIconButton(
                        amIconsType = AmIcons.Close,
                        positive = false,
                        onClick = appBarAction.onCancel
                    )
                },
                actions = {
                    AmFilledTonalIconButton(
                        amIconsType = AmIcons.Save,
                        positive = false,
                        onClick = appBarAction.onCreate
                    )
                },
            )
        }

        is AppBarAction.Read -> {
            TopAppBar(
                modifier = modifier,
                title = {
                    AmText(
                        modifier = Modifier,
                        text = appBarAction.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    AmFilledTonalIconButton(
                        amIconsType = AmIcons.ArrowBack,
                        positive = false,
                        onClick = appBarAction.onBack
                    )
                },
                actions = {},
            )
        }

        is AppBarAction.Edit -> {
            TopAppBar(
                modifier = modifier,
                title = {
                    AmText(
                        modifier = Modifier.widthIn(max = 200.dp),
                        text = appBarAction.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    AmFilledTonalIconButton(
                        amIconsType = AmIcons.Close,
                        positive = false,
                        onClick = appBarAction.onCancel
                    )
                },
                actions = {
                    AmFilledTonalIconButton(
                        amIconsType = AmIcons.Save,
                        positive = false,
                        onClick = appBarAction.onUpdate
                    )
                },
            )
        }

        is AppBarAction.MainNavigation -> {}
    }

}

@Preview
@Composable
fun CustomAppBarPreview() {
    AmAppBar(
        modifier = Modifier,
        appBarAction = AppBarAction.Create("TITLE", {}, {}),
        onClickProfile = {},
        onSearchKeyChange = {}
    )
}