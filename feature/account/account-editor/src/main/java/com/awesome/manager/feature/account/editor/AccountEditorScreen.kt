package com.awesome.manager.feature.account.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.get
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun AccountEditorRoute(
    accountEditorViewModel: AccountEditorViewModel= hiltViewModel()
){

    val accountEditorState=accountEditorViewModel.accountEditorState


    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState:AccountEditorState){

    val accountId=accountEditorState.accountId.collectAsStateWithLifecycle().value

    ScreenPlaceHolder(
        title = "ACCOUNT EDITOR ACCOUNT_ID: $accountId",
        textButton = "CLICK ME",
        onClickButton = {}
    )
}