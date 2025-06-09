package com.awesome.manager.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AuthRoute

fun NavController.navigateToAuth(navOptions: NavOptions){
    navigate(
        route = AuthRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.authScreen(){
    composable<AuthRoute> {
        AuthRoute()
    }
}