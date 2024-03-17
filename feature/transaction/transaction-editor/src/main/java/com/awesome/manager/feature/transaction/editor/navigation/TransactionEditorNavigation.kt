package com.awesome.manager.feature.transaction.editor.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.awesome.manager.core.designsystem.ui_actions.MainActions
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

fun NavHostController.navigateToCreateTransaction(
    accountId: String?, navOptions: NavOptions? = null
) {
    navigate(route = "$transactionEditorRoute/${accountId}/${null}", navOptions = navOptions)
}

fun NavHostController.navigateToEditTransaction(
    transactionId: String, navOptions: NavOptions? = null
) {
    navigate(route = "$transactionEditorRoute/${null}/$transactionId", navOptions = navOptions)
}

fun NavGraphBuilder.transactionEditorScreen(sendMainAction: (MainActions) -> Unit) {
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
        TransactionEditorRoute(sendMainAction = sendMainAction)
    }
}