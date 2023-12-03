package com.awesome.manager.feature.account.accounts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.feature.account.accounts.AccountsRoute

const val accountsRoute = "accounts_route"

fun NavHostController.navigateToAccounts(navOptions: NavOptions?) {
    navigate(route = accountsRoute, navOptions = navOptions)
}

fun NavGraphBuilder.accountsScreen(
    navigateToCreateAccount: () -> Unit,
    navigateToAccountDetails: (AmAccount) -> Unit,
    navigateToCreateTransaction: (AmAccount) -> Unit,
    updateAppBarState: (appBarData: AppBarData?) -> Unit
) {
    composable(route = accountsRoute) {
        AccountsRoute(
            navigateToCreateAccount = navigateToCreateAccount,
            navigateToAccountDetails = navigateToAccountDetails,
            navigateToCreateTransaction = navigateToCreateTransaction,
            updateAppBarState = updateAppBarState
        )
    }
}