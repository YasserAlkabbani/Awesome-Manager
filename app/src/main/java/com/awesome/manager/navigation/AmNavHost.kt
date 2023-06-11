package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.feature.account.search_filter.navigation.accountsScreen
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
    startDistinction: String= introRoute,
    onSelectAccount:(AmAccount)->Unit,
    onSelectTransaction:(AmTransaction)->Unit
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
        accountsScreen(onSelectAccount=onSelectAccount)
        transactionsScreen(onSelectTransaction=onSelectTransaction)
        menuScreen()

    }

}