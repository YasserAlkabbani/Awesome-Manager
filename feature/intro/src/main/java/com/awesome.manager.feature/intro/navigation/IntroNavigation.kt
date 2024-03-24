package com.awesome.manager.feature.intro.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.feature.intro.IntroRoute

const val introRoute: String = "intro_route"

fun NavHostController.navigateToIntro(navOptions: NavOptions? = null) {
    navigate(route = introRoute, navOptions = navOptions)
}

fun NavGraphBuilder.introScreen(sendMainAction: (MainActions) -> Unit) {
    composable(introRoute) {
        IntroRoute(sendMainAction)
    }

}