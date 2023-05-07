package com.awesome.manager.feature.menu.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.awesome.manager.feature.menu.MenuRoute

const val menuRoute:String="menu_route"

fun NavHostController.navigateToMenu(navOptions: NavOptions?){
    navigate(route = menuRoute,navOptions=navOptions)
}


fun NavGraphBuilder.menuScreen(){
    composable(menuRoute){
        MenuRoute()
    }
}