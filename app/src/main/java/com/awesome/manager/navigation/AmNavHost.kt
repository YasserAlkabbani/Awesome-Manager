package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.ui_actions.navigation.NavigationDestination
import com.awesome.manager.feature.account.accounts.AccountsRoute
import com.awesome.manager.feature.account.details.AccountDetailsRoute
import com.awesome.manager.feature.account.editor.AccountEditorRoute
import com.awesome.manager.feature.auth.AuthRoute
import com.awesome.manager.feature.home.HomeRoute
import com.awesome.manager.feature.intro.IntroRoute
import com.awesome.manager.feature.transaction.details.TransactionDetailsRoute
import com.awesome.manager.feature.transaction.editor.TransactionEditorRoute
import com.awesome.manager.feature.transaction.transactions.TransactionsRoute
import timber.log.Timber


@Composable
fun AmNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    startDistinction: NavigationDestination = NavigationDestination.Intro,
    sendMainAction: (MainAction) -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDistinction,
    ) {

        composable<NavigationDestination.Intro> {
            IntroRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationDestination.Auth> {
            AuthRoute(sendMainAction = sendMainAction)
        }



        composable<NavigationDestination.Home> {
            HomeRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationDestination.Accounts> {
            AccountsRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationDestination.Transactions> {
            TransactionsRoute(sendMainAction = sendMainAction)
        }



        composable<NavigationDestination.AccountDetails> {
            AccountDetailsRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationDestination.TransactionDetails> {
            TransactionDetailsRoute(sendMainAction = sendMainAction)
        }



        composable<NavigationDestination.AccountEditor> {
            AccountEditorRoute(sendMainAction = sendMainAction)
        }
        composable<NavigationDestination.TransactionEditor> {
            TransactionEditorRoute(sendMainAction = sendMainAction)
        }

    }

}

fun NavBackStackEntry.asNavigationDestination(): NavigationDestination =
    tryToGetTheRoute<NavigationDestination.Intro>()
        ?: tryToGetTheRoute<NavigationDestination.Auth>()
        ?: tryToGetTheRoute<NavigationDestination.Home>()
        ?: tryToGetTheRoute<NavigationDestination.Accounts>()
        ?: tryToGetTheRoute<NavigationDestination.Transactions>()
        ?: tryToGetTheRoute<NavigationDestination.AccountDetails>()
        ?: tryToGetTheRoute<NavigationDestination.TransactionDetails>()
        ?: tryToGetTheRoute<NavigationDestination.AccountEditor>()
        ?: tryToGetTheRoute<NavigationDestination.TransactionEditor>()!!

inline fun <reified T : NavigationDestination> NavBackStackEntry.tryToGetTheRoute() =
    try {
        this.toRoute<T>()
    } catch (_: Throwable) {
        null
    }
