package com.awesome.manager.core.ui.actions.main

import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFab
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import kotlinx.serialization.Serializable

sealed interface MainAction {
    fun sendMainAction(sendMainAction: (MainAction) -> Unit, doneCurrentAction: () -> Unit) {
        sendMainAction(this)
        doneCurrentAction()
    }
}

sealed class BottomSheetAction : MainAction {

    data object Dismiss : BottomSheetAction()
    data class Profile(
        val email: String,
        val logout: () -> Unit
    ) : BottomSheetAction()

//    data class SearchWithContent(
//        val searchLabel: String, val initSearch: String,
//        val onReSearch: (String) -> Unit,
//        val onSearchDone: () -> Unit,
//        val content: @Composable () -> Unit,
//    ) : BottomSheetContent()

    data class AccountCreated(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class PasswordRested(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class AuthError(
        val errorMessage: String, val createNewAccount: () -> Unit, val editCredentials: () -> Unit
    ) : BottomSheetAction()

    data class UnknownError(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class ConnectionError(val dismiss: () -> Unit) :
        BottomSheetAction()

    data class CustomError(
        val errorMessage: String, val dismiss: () -> Unit
    ) : BottomSheetAction()

    data class PickDate(
        val initTime: Long, val setDate: (Long) -> Unit, val dismiss: () -> Unit
    ) : BottomSheetAction()

    data class PickRangeDate(
        val initTime: Long, val setDate: (Long, Long) -> Unit, val dismiss: () -> Unit
    ) : BottomSheetAction()

}

sealed class NavigationAction : MainAction {

    data object NavigateUp : NavigationAction()

    @Serializable
    data object Intro : NavigationAction()

    @Serializable
    data object Auth : NavigationAction()

    @Serializable
    data object Home : NavigationAction()

    @Serializable
    data object Accounts : NavigationAction()

    @Serializable
    data object Transactions : NavigationAction()


    @Serializable
    data class AccountDetails(val accountId: String) : NavigationAction()

    @Serializable
    data class TransactionDetails(val transactionId: String) : NavigationAction()


    @Serializable
    data class AccountEditor(val accountId: String?) : NavigationAction()

    @Serializable
    data class TransactionEditor(val accountId: String?, val transactionId: String?) :
        NavigationAction()

}

sealed class DynamicFabAction : MainAction {

    data object None : DynamicFabAction()

    data object Loading:DynamicFabAction()

    data class Fab(
        val dynamicFab: DynamicFab,
        val dynamicFabExtraButton: DynamicFabExtraButton?
    ) : DynamicFabAction()

    data class Message(
        val dynamicFabText: DynamicFabText,
        val dynamicFabExtraButton: DynamicFabExtraButton?
    ) : DynamicFabAction()

    data class Button(
        val dynamicFabButton: DynamicFabButton,
        val dynamicFabExtraButton: DynamicFabExtraButton?
    ) : DynamicFabAction()

}

data class ErrorAction(val amUIError: AmUIError):MainAction

sealed class PickerAction : MainAction






