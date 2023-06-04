package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.awesome.manager.feature.accounts.navigation.accountsScreen
import com.awesome.manager.feature.auth.navigation.authRoute
import com.awesome.manager.feature.auth.navigation.authScreen
import com.awesome.manager.feature.home.navigation.homeScreen
import com.awesome.manager.feature.home.navigation.navigateToHome
import com.awesome.manager.feature.intro.navigation.introRoute
import com.awesome.manager.feature.intro.navigation.introScreen
import com.awesome.manager.feature.menu.navigation.menuScreen
import com.awesome.manager.feature.transactions.navigation.transactionsScreen
import com.awesome.manager.ui.AmAppState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AmNavHost(
    modifier: Modifier,
    amAppState: AmAppState,
    clickFab:StateFlow<Unit?>,
    doneClickFab:()->Unit,
    startDistinction: String= introRoute
){

    val navController=amAppState.navHostController

    NavHost(
        modifier=modifier,
        navController = navController,
        startDestination = startDistinction
    ){

        introScreen()
        authScreen()
        homeScreen()
        accountsScreen(
            clickFab=clickFab,
            doneClickFab=doneClickFab
        )
        transactionsScreen(
            clickFab=clickFab,
            doneClickFab=doneClickFab
        )
        menuScreen()

    }

}