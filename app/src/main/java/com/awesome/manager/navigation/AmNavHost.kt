package com.awesome.manager.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.awesome.manager.feature.account.details.navigation.accountDetailsScreen
import com.awesome.manager.feature.account.editor.navigation.accountEditorScreen
import com.awesome.manager.feature.account.accounts.navigation.accountsScreen
import com.awesome.manager.feature.account.editor.navigation.navigateToAccountEditor
import com.awesome.manager.feature.auth.navigation.authScreen
import com.awesome.manager.feature.home.navigation.homeScreen
import com.awesome.manager.feature.intro.navigation.introRoute
import com.awesome.manager.feature.intro.navigation.introScreen
import com.awesome.manager.feature.menu.navigation.menuScreen
import com.awesome.manager.feature.transaction.details.navigation.transactionDetailsScreen
import com.awesome.manager.feature.transaction.editor.navigation.transactionEditorScreen
import com.awesome.manager.feature.transaction.transactions.navigation.transactionsScreen
import com.awesome.manager.ui.AmAppState
import timber.log.Timber

@Composable
fun AmNavHost(
    modifier: Modifier,
    amAppState: AmAppState,
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
            navigateToAccountDetails = {
                Timber.d("TEST_ACCOUNT_NAVIGATION ACCOUNT_DETAILS $it")
                navController.navigateToAccountEditor(accountId = it,navOptions = null)
            },
            navigateToCreateAccount = { navController.navigateToAccountEditor(accountId = null,navOptions = null) }
        )
        accountEditorScreen()
        accountDetailsScreen()

        transactionsScreen({},{})
        transactionEditorScreen()
        transactionDetailsScreen()

        menuScreen()

    }

}