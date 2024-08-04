package com.awesome.manager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
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

//inline fun <reified T : NavigationDestination> NavGraphBuilder.composableWithAnimation(noinline content: @Composable () -> Unit) {
//    composable<T>(
//        enterTransition = {
//            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
//        },
//        exitTransition ={
//            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500))
//        },
//        popEnterTransition ={
//            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500))
//        },
//        popExitTransition ={
//            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500))
//        }
//    ) {
//        content()
//    }
//}

fun NavBackStackEntry.asNavigationDestination(): NavigationDestination? = when (destination.route) {
    NavigationDestination.Intro::class.qualifiedName -> toRoute<NavigationDestination.Intro>()
    NavigationDestination.Auth::class.qualifiedName -> toRoute<NavigationDestination.Auth>()
    NavigationDestination.Home::class.qualifiedName -> toRoute<NavigationDestination.Home>()
    NavigationDestination.Accounts::class.qualifiedName -> toRoute<NavigationDestination.Accounts>()
    NavigationDestination.Transactions::class.qualifiedName -> toRoute<NavigationDestination.Transactions>()
    NavigationDestination.AccountDetails::class.qualifiedName -> toRoute<NavigationDestination.AccountDetails>()
    NavigationDestination.TransactionDetails::class.qualifiedName -> toRoute<NavigationDestination.TransactionDetails>()
    NavigationDestination.AccountEditor::class.qualifiedName -> toRoute<NavigationDestination.AccountEditor>()
    NavigationDestination.TransactionEditor::class.qualifiedName -> toRoute<NavigationDestination.TransactionEditor>()
    else -> null
}