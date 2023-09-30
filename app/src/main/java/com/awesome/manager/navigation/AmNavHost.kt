package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.feature.account.details.navigation.accountDetailsScreen
import com.awesome.manager.feature.account.editor.navigation.accountEditorScreen
import com.awesome.manager.feature.account.accounts.navigation.accountsScreen
import com.awesome.manager.feature.account.details.navigation.navigateToAccountDetails
import com.awesome.manager.feature.account.editor.navigation.navigateToCreateAccount
import com.awesome.manager.feature.account.editor.navigation.navigateToEditAccount
import com.awesome.manager.feature.auth.navigation.authScreen
import com.awesome.manager.feature.home.navigation.homeScreen
import com.awesome.manager.feature.intro.navigation.introRoute
import com.awesome.manager.feature.intro.navigation.introScreen
import com.awesome.manager.feature.menu.navigation.menuScreen
import com.awesome.manager.feature.transaction.details.navigation.navigateToTransactionDetails
import com.awesome.manager.feature.transaction.details.navigation.transactionDetailsScreen
import com.awesome.manager.feature.transaction.editor.navigation.navigateToCreateTransaction
import com.awesome.manager.feature.transaction.editor.navigation.navigateToEditTransaction
import com.awesome.manager.feature.transaction.editor.navigation.transactionEditorScreen
import com.awesome.manager.feature.transaction.transactions.navigation.transactionsScreen
import com.awesome.manager.ui.AmAppState

@Composable
fun AmNavHost(
    modifier: Modifier,
    amAppState: AmAppState,
    startDistinction: String = introRoute,
    updateAppBarState: (appBarData: AppBarData?) -> Unit
) {

    val navController = amAppState.navHostController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDistinction,
    ) {

        introScreen()
        authScreen()
        homeScreen()

        accountsScreen(
            navigateToCreateAccount = { navController.navigateToCreateAccount(navOptions = null) },
            navigateToAccountDetails = { accountId ->
                navController.navigateToAccountDetails(
                    accountId = accountId,
                    navOptions = null
                )
            },
            navigateToCreateTransaction = { accountId ->
                navController.navigateToCreateTransaction(
                    accountId = accountId,
                    navOptions = null
                )
            },
            updateAppBarState = updateAppBarState
        )
        accountEditorScreen(
            navController::popBackStack,
            updateAppBarState = updateAppBarState
        )
        accountDetailsScreen(
            navigateBack = navController::popBackStack,
            navigateToEditAccount = { transactionId ->
                navController.navigateToEditAccount(
                    accountId = transactionId,
                    navOptions = null
                )
            },
            navigateCreateTransaction = {
                navController.navigateToCreateTransaction(
                    accountId = it,
                    navOptions = null
                )
            },
            navigateToTransaction = { transactionId ->
                navController.navigateToTransactionDetails(
                    transactionId = transactionId,
                    navOptions = null
                )
            },
            updateAppBarState = updateAppBarState
        )

        transactionsScreen({}, {})
        transactionEditorScreen(
            onBack = { navController.popBackStack() },
            updateAppBarState = updateAppBarState
        )
        transactionDetailsScreen()

        menuScreen()

    }

}