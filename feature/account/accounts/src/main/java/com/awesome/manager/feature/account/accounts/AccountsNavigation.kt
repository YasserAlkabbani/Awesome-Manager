package com.awesome.manager.feature.account.accounts

import android.accounts.Account
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AccountsRoute

fun NavController.navigateToAccounts(navOptions: NavOptions?) {
    navigate(
        route = AccountsRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.accountsScreen(navigateToCreateAccount: ()->Unit) {
    composable<AccountsRoute> {
        AccountsScreen(navigateToCreateAccount=navigateToCreateAccount)
    }
}