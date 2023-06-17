package com.awesome.manager.feature.account.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.account.details.AccountDetailsRoute

const val accountDetailsRoute:String="account_details_route"

fun NavHostController.navigateToAccountDetails(navOptions: NavOptions?){
    navigate(route = accountDetailsRoute,navOptions=navOptions)
}

fun NavGraphBuilder.accountDetailsScreen(){
    composable(accountDetailsRoute){
        AccountDetailsRoute()
    }
}