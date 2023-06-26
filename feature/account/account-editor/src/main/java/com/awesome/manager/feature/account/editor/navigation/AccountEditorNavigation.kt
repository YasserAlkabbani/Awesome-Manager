package com.awesome.manager.feature.account.editor.navigation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.FloatingWindow
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.dialog
import androidx.navigation.get
import androidx.navigation.navArgument
import com.awesome.manager.core.ui.DialogDestinationContainer
import com.awesome.manager.feature.account.editor.AccountEditorRoute

private const val ACCOUNT_EDITOR_ROUTE:String="account_editor_Route"
private const val ARG_ACCOUNT_ID:String="account_id"


internal class AccountEditorArg(val accountId:String?){
    constructor(savedStateHandle: SavedStateHandle) : this((Uri.decode(savedStateHandle[ARG_ACCOUNT_ID])))
}

fun NavHostController.navigateToEditAccount(accountId:String, navOptions: NavOptions?){
    val encodedId = Uri.encode(accountId)
    navigate(
        route = "$ACCOUNT_EDITOR_ROUTE/$encodedId",
        navOptions=navOptions,
    )
}
fun NavHostController.navigateToCreateAccount(navOptions: NavOptions?){
    navigate(
        route = "$ACCOUNT_EDITOR_ROUTE/${null}",
        navOptions=navOptions,
    )
}
fun NavGraphBuilder.accountEditorScreen(){
    dialog(
        route="$ACCOUNT_EDITOR_ROUTE/{$ARG_ACCOUNT_ID}",
        arguments = listOf(navArgument(ARG_ACCOUNT_ID) {
            type = NavType.StringType
            nullable=true
            defaultValue=null
        }),
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            decorFitsSystemWindows = true,
        ),
    ){
        DialogDestinationContainer{
            AccountEditorRoute()
        }
    }
}