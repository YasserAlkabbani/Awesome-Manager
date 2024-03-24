package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.feature.account.details.navigation.accountDetailsScreen
import com.awesome.manager.feature.account.editor.navigation.accountEditorScreen
import com.awesome.manager.feature.account.accounts.navigation.accountsScreen
import com.awesome.manager.feature.auth.navigation.authScreen
import com.awesome.manager.feature.home.navigation.homeScreen
import com.awesome.manager.feature.intro.navigation.introRoute
import com.awesome.manager.feature.intro.navigation.introScreen
import com.awesome.manager.feature.menu.navigation.menuScreen
import com.awesome.manager.feature.transaction.details.navigation.transactionDetailsScreen
import com.awesome.manager.feature.transaction.editor.navigation.transactionEditorScreen
import com.awesome.manager.feature.transaction.transactions.navigation.transactionsScreen

@Composable
fun AmNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    startDistinction: String = introRoute,
    sendMainAction: (MainActions) -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDistinction,
    ) {

        introScreen(sendMainAction = sendMainAction)
        authScreen(sendMainAction = sendMainAction)
        homeScreen(sendMainAction = sendMainAction)

        accountsScreen(sendMainAction = sendMainAction)
        accountEditorScreen(sendMainAction = sendMainAction)
        accountDetailsScreen(sendMainAction = sendMainAction)

        transactionsScreen(sendMainAction = sendMainAction)
        transactionEditorScreen(sendMainAction = sendMainAction)
        transactionDetailsScreen(sendMainAction = sendMainAction)

        menuScreen()

    }

}