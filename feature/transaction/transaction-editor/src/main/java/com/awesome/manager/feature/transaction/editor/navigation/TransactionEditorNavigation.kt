package com.awesome.manager.feature.transaction.editor.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import com.awesome.manager.feature.transaction.editor.TransactionEditorRoute

private const val transactionEditorRoute:String="transaction_editor_route"

fun NavHostController.navigateToTransactionEditor(transactionId:String?,navOptions: NavOptions?){
    navigate(route = transactionEditorRoute,navOptions=navOptions)
}

fun NavGraphBuilder.transactionEditorScreen(){
    dialog(transactionEditorRoute){
        TransactionEditorRoute()
    }
}