package com.awesome.manager.feature.account.editor.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.dialog
import androidx.navigation.get
import androidx.navigation.navArgument
import com.awesome.manager.feature.account.editor.AccountEditorRoute
import com.awesome.manager.feature.account.editor.AccountEditorScreen

private const val ACCOUNT_EDITOR_ROUTE:String="account_editor_Route"
private const val ARG_ACCOUNT_ID:String="account_id"


internal class AccountEditorArg(val accountId:String?){
    constructor(savedStateHandle: SavedStateHandle) : this((Uri.decode(savedStateHandle[ARG_ACCOUNT_ID])))
}

fun NavHostController.navigateToAccountEditor(accountId:String?,navOptions: NavOptions?){
    val encodedId = Uri.encode(accountId)
    navigate(
        route = "$ACCOUNT_EDITOR_ROUTE/$encodedId",
        navOptions=navOptions
    )
}

fun NavGraphBuilder.accountEditorScreen(){
    bottomSheet(
        route="$ACCOUNT_EDITOR_ROUTE/{$ARG_ACCOUNT_ID}",
        arguments = listOf(navArgument(ARG_ACCOUNT_ID) { type = NavType.StringType }),
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        AccountEditorRoute()
    }
}


public fun NavGraphBuilder.bottomSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    addDestination(
        DialogNavigator.Destination(
            provider[DialogNavigator::class],
            dialogProperties,
            content
        ).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}