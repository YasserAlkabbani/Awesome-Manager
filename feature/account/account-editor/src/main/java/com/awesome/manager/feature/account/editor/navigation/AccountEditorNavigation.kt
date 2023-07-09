package com.awesome.manager.feature.account.editor.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.awesome.manager.feature.account.editor.AccountEditorRoute

const val accountEditorRoute:String="account_editor_Route"
private const val ARG_ACCOUNT_ID:String="account_id"


internal class AccountEditorArg(val accountId:String?){
    constructor(savedStateHandle: SavedStateHandle) : this((Uri.decode(savedStateHandle[ARG_ACCOUNT_ID])))
}

fun NavHostController.navigateToEditAccount(accountId:String, navOptions: NavOptions?){
    val encodedId = Uri.encode(accountId)
    navigate(
        route = "$accountEditorRoute/$encodedId",
        navOptions=navOptions,
    )
}
fun NavHostController.navigateToCreateAccount(navOptions: NavOptions?){
    navigate(
        route = "$accountEditorRoute/${null}",
        navOptions=navOptions,
    )
}
fun NavGraphBuilder.accountEditorScreen(onBack:()->Unit){
    composable(
        route="$accountEditorRoute/{$ARG_ACCOUNT_ID}",
        arguments = listOf(navArgument(ARG_ACCOUNT_ID) {
            type = NavType.StringType
            nullable=true
            defaultValue=null
        }),
    ){
        it.destination
        AccountEditorRoute(onBack = onBack)
    }
}