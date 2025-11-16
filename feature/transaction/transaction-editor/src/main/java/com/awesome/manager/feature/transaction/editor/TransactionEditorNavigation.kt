package com.awesome.manager.feature.transaction.editor

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class TransactionEditorRoute(val accountID: String?, val transactionID: String?)


fun NavController.navigateToTransactionEditor(
    accountID: String,
    transactionID: String,
    navOptions: NavOptions?=null
) {
    navigate(
        route = TransactionEditorRoute(
            accountID = accountID,
            transactionID = transactionID
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateToTransactionCreator(
    accountID: String? = null,
    navOptions: NavOptions? = null
) {
    navigate(
        route = TransactionEditorRoute(
            accountID = accountID,
            transactionID = null
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.transactionEditorScreen() {
    composable<TransactionEditorRoute> {
        TransactionEditorScreen()
    }
}