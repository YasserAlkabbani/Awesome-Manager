package com.awesome.manager.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.auth.AuthRoute

const val authRoute: String = "auth_route"

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(route = authRoute, navOptions = navOptions)
}

fun NavGraphBuilder.authScreen() {
    composable(authRoute) {
        AuthRoute()
    }
}