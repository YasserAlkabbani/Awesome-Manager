package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.awesome.manager.feature.accounts.navigation.accountsScreen
import com.awesome.manager.feature.auth.navigation.authRoute
import com.awesome.manager.feature.auth.navigation.authScreen
import com.awesome.manager.feature.home.navigation.homeScreen
import com.awesome.manager.feature.menu.navigation.menuScreen
import com.awesome.manager.feature.transactions.navigation.transactionsScreen
import com.awesome.manager.ui.AmAppState

@Composable
fun AmNavHost(
    modifier: Modifier,
    amAppState: AmAppState,
    startDistinction: String= authRoute
){

    val navController=amAppState.navHostController

    NavHost(
        modifier=modifier,
        navController = navController,
        startDestination = startDistinction
    ){

        authScreen()
        homeScreen()
        accountsScreen()
        transactionsScreen()
        menuScreen()

    }

}