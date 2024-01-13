package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
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
    updateAppBarState: (appBarData: AppBarData) -> Unit,
    showProfileBottomSheet: () -> Unit,
) {

    val navController = amAppState.navHostController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDistinction,
    ) {

        introScreen()
        authScreen()
        homeScreen(
            showProfileBottomSheet = showProfileBottomSheet
        )

        accountsScreen(
            navigateToCreateAccount = { navController.navigateToCreateAccount(navOptions = null) },
            navigateToAccountDetails = { amAccount ->
                navController.navigateToAccountDetails(
                    accountId = amAccount.id,
                    navOptions = null
                )
            },
            navigateToCreateTransaction = { amAccount ->
                navController.navigateToCreateTransaction(
                    accountId = amAccount.id,
                    navOptions = null
                )
            },
            showProfileBottomSheet = showProfileBottomSheet
        )
        accountEditorScreen(
            navController::popBackStack,
            updateAppBarState = updateAppBarState
        )
        accountDetailsScreen(
            navigateBack = navController::popBackStack,
            navigateToEditAccount = { amAccount ->
                navController.navigateToEditAccount(
                    accountId = amAccount.id,
                    navOptions = null
                )
            },
            navigateCreateTransaction = { amAccount ->
                navController.navigateToCreateTransaction(
                    accountId = amAccount.id,
                    navOptions = null
                )
            },
            navigateToTransactionDetails = { amTransaction ->
                navController.navigateToTransactionDetails(
                    transactionId = amTransaction.id,
                    navOptions = null
                )
            },
            updateAppBarState = updateAppBarState
        )

        transactionsScreen(
            navigateToTransactionDetails = { amTransaction ->
                navController.navigateToTransactionDetails(
                    transactionId = amTransaction.id,
                    navOptions = null
                )
            },
            showProfileBottomSheet = showProfileBottomSheet
        )
        transactionEditorScreen(
            onBack = { navController.popBackStack() },
            updateAppBarState = updateAppBarState
        )
        transactionDetailsScreen(
            navigateToAccount = { amAccount ->
                navController.navigateToEditAccount(amAccount.id, navOptions = null)
            },
            navigateToCreateTransaction = { amAccount ->
                navController.navigateToCreateTransaction(
                    accountId = amAccount.id,
                    navOptions = null
                )
            },
            navigateToEditTransaction = { amTransaction ->
                navController.navigateToEditTransaction(
                    transactionId = amTransaction.id,
                    navOptions = null
                )
            },
            navigateBack = navController::popBackStack,
            updateAppBarState = updateAppBarState,
        )

        menuScreen()

    }

}