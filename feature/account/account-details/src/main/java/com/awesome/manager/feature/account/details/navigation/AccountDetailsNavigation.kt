package com.awesome.manager.feature.account.details.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.account.details.AccountDetailsRoute

const val accountDetailsRoute:String="account_details_route"
private const val ARG_ACCOUNT_ID:String="account_id"

internal class AccountDetailsArg(val accountId:String){
    constructor(savedStateHandle: SavedStateHandle):this(Uri.decode(savedStateHandle[ARG_ACCOUNT_ID]))
}

fun NavHostController.navigateToAccountDetails(accountId:String,navOptions: NavOptions?){
    navigate(
        route = "$accountDetailsRoute/${accountId}",
        navOptions=navOptions
    )
}

fun NavGraphBuilder.accountDetailsScreen(
    navigateBack:()->Unit,
    navigateToTransaction:(transactionId:String)->Unit
){
    composable(
        route="$accountDetailsRoute/{$ARG_ACCOUNT_ID}",
        content = {
            AccountDetailsRoute(
                navigateBack=navigateBack,
                navigateToTransaction = navigateToTransaction
            )
        }
    )
}