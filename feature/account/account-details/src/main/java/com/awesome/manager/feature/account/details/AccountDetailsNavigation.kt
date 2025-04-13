package com.awesome.manager.feature.account.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class AccountDetailsRoute(val accountID: String)


fun NavController.navigateToAccountDetails(
    accountID: String,
    navOptions: NavOptions
) {
    navigate(
        route = AccountDetailsRoute(accountID = accountID),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.accountDetailsScreen() {
    composable<AccountDetailsRoute> {
        AccountDetailsScreen()
    }
}