package com.awesome.manager.feature.transaction.editor.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.feature.transaction.editor.TransactionEditorRoute

const val transactionEditorRoute: String = "transaction_editor_route"
private const val TRANSACTION_ID: String = "transaction_id"
private const val ACCOUNT_ID: String = "account_id"

internal class TransactionEditorArg(val accountId: String?, val transactionId: String?) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        Uri.encode(savedStateHandle[ACCOUNT_ID]),
        Uri.encode(savedStateHandle[TRANSACTION_ID])
    )
}

fun NavHostController.navigateToCreateTransaction(accountId: String?, navOptions: NavOptions?) {
    navigate(route = "$transactionEditorRoute/${accountId}/${null}", navOptions = navOptions)
}

fun NavHostController.navigateToEditTransaction(
    transactionId: String,
    navOptions: NavOptions?
) {
    navigate(route = "$transactionEditorRoute/${null}/$transactionId", navOptions = navOptions)
}

fun NavGraphBuilder.transactionEditorScreen(
    onBack: () -> Unit,
    updateAppBarState: (appBarData: AppBarData) -> Unit
) {
    composable(
        route = "$transactionEditorRoute/{$ACCOUNT_ID}/{$TRANSACTION_ID}",
        arguments = listOf(
            navArgument(ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
            },
            navArgument(TRANSACTION_ID) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        TransactionEditorRoute(
            onBack = onBack,
            updateAppBarState = updateAppBarState
        )
    }
}