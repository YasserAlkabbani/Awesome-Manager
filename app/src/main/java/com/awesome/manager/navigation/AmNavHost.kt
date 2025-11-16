package com.awesome.manager.navigation

import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.awesome.manager.feature.account.accounts.accountsScreen
import com.awesome.manager.feature.account.details.accountDetailsScreen
import com.awesome.manager.feature.account.details.navigateToAccountDetails
import com.awesome.manager.feature.account.editor.accountEditorScreen
import com.awesome.manager.feature.account.editor.navigateToAccountCreator
import com.awesome.manager.feature.account.editor.navigateToAccountEditor
import com.awesome.manager.feature.auth.AuthRoute
import com.awesome.manager.feature.auth.authScreen
import com.awesome.manager.feature.home.homeScreen
import com.awesome.manager.feature.transaction.details.navigateToTransactionDetails
import com.awesome.manager.feature.transaction.details.transactionDetailsScreen
import com.awesome.manager.feature.transaction.editor.navigateToTransactionCreator
import com.awesome.manager.feature.transaction.editor.navigateToTransactionEditor
import com.awesome.manager.feature.transaction.editor.transactionEditorScreen
import com.awesome.manager.feature.transaction.transactions.transactionsScreen


@Composable
fun AmNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
) {
    ModalBottomSheetLayout(
        modifier = Modifier,
        bottomSheetNavigator = bottomSheetNavigator,
        sheetGesturesEnabled = false,
    ) {
        NavHost(
            modifier = modifier,
            navController = navHostController,
            startDestination = AuthRoute,
        ) {

            homeScreen(
                navigateToCreateAccount = {
                    navHostController.navigateToAccountCreator(null)
                }
            )

            accountsScreen(
                navigateToCreateAccount = {
                    navHostController.navigateToAccountCreator(null)
                },
                navigateToAccountDetails = { accountID ->
                    navHostController.navigateToAccountDetails(
                        accountID = accountID,
                        navOptions = navOptions { })
                },
                navigateToCreateTransaction = { accountID ->
                    navHostController.navigateToTransactionCreator(accountID = accountID)
                }
            )
            accountDetailsScreen(
                navigateToCreateTransaction = navHostController::navigateToTransactionCreator,
                navigateToEditAccount = navHostController::navigateToAccountEditor,
                navigateBack = navHostController::popBackStack,
            )
            accountEditorScreen(navHostController::popBackStack)


            transactionsScreen(
                navigateToCreateTransaction = navHostController::navigateToTransactionCreator,
                navigateToTransactionDetails = navHostController::navigateToTransactionDetails,
            )
            transactionDetailsScreen(
                navigateToAccountDetails = navHostController::navigateToAccountDetails,
                navigateToTransactionEditor = navHostController::navigateToTransactionEditor
            )
            transactionEditorScreen()


            authScreen()

            bottomSheet<BottomSheetNavigation.ConnectionError> {

            }
            bottomSheet<BottomSheetNavigation.UnauthorizedError> {

            }
            bottomSheet<BottomSheetNavigation.PoorConnectionError> {

            }
            bottomSheet<BottomSheetNavigation.UnknownError> {

            }
            bottomSheet<BottomSheetNavigation.NoDataError> {

            }
            bottomSheet<BottomSheetNavigation.NoPermissionError> {

            }
            bottomSheet<BottomSheetNavigation.BadRequestError> {

            }
            bottomSheet<BottomSheetNavigation.OtherUIError> {

            }

        }
    }

}

//mainAction is NavigationAction.AccountDetails -> navOptions {
//    if (currentNavigationDestination is NavigationAction.AccountEditor) {
//        popUpTo(currentNavigationDestination) {
//            inclusive = true
//        }
//        launchSingleTop = true
//    }
//}
//
//mainAction is NavigationAction.TransactionDetails -> navOptions {
//    if (currentNavigationDestination is NavigationAction.TransactionEditor) {
//        popUpTo(currentNavigationDestination) {
//            inclusive = true
//        }
//        launchSingleTop = true
//    }
//}

//fun NavDestination.isRouteFrom(route: KClass<*>) = hasRoute(route = route)
//
//fun NavBackStackEntry.asNavigationDestination(): AmDestinations? =
//    when (destination.route?.substringBefore("/")) {
//        MainDestinations.Home::class.qualifiedName -> toRoute<MainDestinations.Home>()
//        MainDestinations.Accounts::class.qualifiedName -> toRoute<AmDestinations.Accounts>()
//        MainDestinations.Transactions::class.qualifiedName -> toRoute<AmDestinations.Transactions>()
//        AmDestinations.AccountDetails::class.qualifiedName -> toRoute<AmDestinations.AccountDetails>()
//        AmDestinations.TransactionDetails::class.qualifiedName -> toRoute<AmDestinations.TransactionDetails>()
//        AmDestinations.AccountEditor::class.qualifiedName -> toRoute<AmDestinations.AccountEditor>()
//        AmDestinations.TransactionEditor::class.qualifiedName -> toRoute<AmDestinations.TransactionEditor>()
//        else -> null
//    }
//
//fun NavBackStackEntry.asBottomSheetNavigation(): BottomSheetNavigation? =
//    when (destination.route?.substringBefore("/")) {
//        BottomSheetNavigation.ConnectionError::class.qualifiedName -> toRoute<BottomSheetNavigation.ConnectionError>()
//        BottomSheetNavigation.UnauthorizedError::class.qualifiedName -> toRoute<BottomSheetNavigation.UnauthorizedError>()
//        BottomSheetNavigation.PoorConnectionError::class.qualifiedName -> toRoute<BottomSheetNavigation.PoorConnectionError>()
//        BottomSheetNavigation.UnknownError::class.qualifiedName -> toRoute<BottomSheetNavigation.UnknownError>()
//        BottomSheetNavigation.NoDataError::class.qualifiedName -> toRoute<BottomSheetNavigation.NoDataError>()
//        BottomSheetNavigation.NoPermissionError::class.qualifiedName -> toRoute<BottomSheetNavigation.NoPermissionError>()
//        BottomSheetNavigation.BadRequestError::class.qualifiedName -> toRoute<BottomSheetNavigation.BadRequestError>()
//        BottomSheetNavigation.OtherUIError::class.qualifiedName -> toRoute<BottomSheetNavigation.OtherUIError>()
//        else -> null
//    }