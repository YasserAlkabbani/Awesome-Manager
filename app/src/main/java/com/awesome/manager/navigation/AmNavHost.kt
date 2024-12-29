package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.ui.actions.main.NavigationAction
import com.awesome.manager.feature.account.accounts.AccountsRoute
import com.awesome.manager.feature.account.details.AccountDetailsRoute
import com.awesome.manager.feature.account.editor.AccountEditorRoute
import com.awesome.manager.feature.auth.AuthRoute
import com.awesome.manager.feature.home.HomeRoute
import com.awesome.manager.feature.intro.IntroRoute
import com.awesome.manager.feature.transaction.details.TransactionDetailsRoute
import com.awesome.manager.feature.transaction.editor.TransactionEditorRoute
import com.awesome.manager.feature.transaction.transactions.TransactionsRoute


@Composable
fun AmNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    startDistinction: NavigationAction = NavigationAction.Intro,
    sendMainAction: (MainAction) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDistinction,
    ) {
        composable<NavigationAction.Intro> {
            IntroRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.Auth> {
            AuthRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.Home> {
            HomeRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.Accounts> {
            AccountsRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.Transactions> {
            TransactionsRoute(sendMainAction = sendMainAction)
        }

        composable<NavigationAction.AccountDetails> {
            AccountDetailsRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.TransactionDetails> {
            TransactionDetailsRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.AccountEditor> {
            AccountEditorRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationAction.TransactionEditor> {
            TransactionEditorRoute(sendMainAction = sendMainAction)
        }
    }

}

fun NavBackStackEntry.asNavigationDestination(): NavigationAction =
    when (destination.route?.substringBefore("/")) {
        NavigationAction.Intro::class.qualifiedName -> toRoute<NavigationAction.Intro>()
        NavigationAction.Auth::class.qualifiedName -> toRoute<NavigationAction.Auth>()
        NavigationAction.Home::class.qualifiedName -> toRoute<NavigationAction.Home>()
        NavigationAction.Accounts::class.qualifiedName -> toRoute<NavigationAction.Accounts>()
        NavigationAction.Transactions::class.qualifiedName -> toRoute<NavigationAction.Transactions>()
        NavigationAction.AccountDetails::class.qualifiedName -> toRoute<NavigationAction.AccountDetails>()
        NavigationAction.TransactionDetails::class.qualifiedName -> toRoute<NavigationAction.TransactionDetails>()
        NavigationAction.AccountEditor::class.qualifiedName -> toRoute<NavigationAction.AccountEditor>()
        NavigationAction.TransactionEditor::class.qualifiedName -> toRoute<NavigationAction.TransactionEditor>()
        else -> throw ClassNotFoundException("NAVIGATION DESTINATION NOT FOUND")
    }