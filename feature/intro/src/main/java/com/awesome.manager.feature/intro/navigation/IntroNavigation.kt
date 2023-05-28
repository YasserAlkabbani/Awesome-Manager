package com.awesome.manager.feature.intro.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.intro.IntroRoute

const val introRoute:String="intro_route"

fun NavHostController.navigateToIntro(navOptions: NavOptions?){
    navigate(route = introRoute,navOptions=navOptions)
}

fun NavGraphBuilder.introScreen(){
    composable(introRoute){
        IntroRoute()
    }

}