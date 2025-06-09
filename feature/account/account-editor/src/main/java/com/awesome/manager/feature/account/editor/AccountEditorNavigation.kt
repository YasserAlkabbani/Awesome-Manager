package com.awesome.manager.feature.account.editor

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class AccountEditorRoute(val accountID: String?)

fun NavController.navigateAccountEditor(
    accountID: String,
    navOptions: NavOptions
) {
    navigate(
        route = AccountEditorRoute(accountID = accountID),
        navOptions = navOptions
    )
}

fun NavController.navigateToAccountCreator(navOptions: NavOptions?) {
    navigate(
        route = AccountEditorRoute(accountID = null),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.accountEditorScreen(popup:()->Unit) {
    composable<AccountEditorRoute> {
        AccountEditorRoute(popup=popup)
    }
}