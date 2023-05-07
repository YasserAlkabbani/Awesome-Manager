package com.awesome.manager.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.home.HomeRoute

const val homeRoute:String="home_route"

fun NavHostController.navigateToHome(navOptions: NavOptions?){
    navigate(route = homeRoute,navOptions=navOptions)
}


fun NavGraphBuilder.homeScreen(){
    composable(homeRoute){
        HomeRoute()
    }
}