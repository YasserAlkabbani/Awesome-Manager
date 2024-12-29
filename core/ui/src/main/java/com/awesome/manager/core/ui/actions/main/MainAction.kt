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

sealed interface BottomSheetAction : MainAction {

    data object AccountCreated : BottomSheetAction

    data object PasswordRested : BottomSheetAction

    data object UnknownError : BottomSheetAction

    data object ConnectionError : BottomSheetAction

    data class CustomError(val errorMessage: String) : BottomSheetAction

    data class Profile(val email: String, val logout: () -> Unit) : BottomSheetAction

    data class PickDate(val initTime: Long, val setDate: (Long) -> Unit) : BottomSheetAction

    data class PickRangeDate(
        val initTime: Long, val setDate: (Long, Long) -> Unit,
    ) : BottomSheetAction

    data class SearchForAccount(
        val initSearch: String, val onSelectAccount: (String) -> Unit
    ) : BottomSheetAction

    data class AuthError(
        val errorMessage: String, val createNewAccount: () -> Unit, val editCredentials: () -> Unit
    ) : BottomSheetAction

}

sealed interface NavigationAction : MainAction {

    data object NavigateUp : NavigationAction

    @Serializable
    data object Intro : NavigationAction

    @Serializable
    data object Auth : NavigationAction

    @Serializable
    data object Home : NavigationAction

    @Serializable
    data object Accounts : NavigationAction

    @Serializable
    data object Transactions : NavigationAction


    @Serializable
    data class AccountDetails(val accountId: String) : NavigationAction

    @Serializable
    data class TransactionDetails(val transactionId: String) : NavigationAction


    @Serializable
    data class AccountEditor(val accountId: String?) : NavigationAction

    @Serializable
    data class TransactionEditor(
        val accountId: String?, val transactionId: String?
    ) : NavigationAction

}

sealed interface DynamicFabAction : MainAction {

    val dynamicFabExtraButton: DynamicFabExtraButton?
    val index: Int


    data object None : DynamicFabAction {
        override val dynamicFabExtraButton: DynamicFabExtraButton? = null
        override val index: Int = 1000
    }

    data object Loading : DynamicFabAction {
        override val dynamicFabExtraButton: DynamicFabExtraButton? = null
        override val index: Int = 2000
    }

    data class Fab(
        override val dynamicFabExtraButton: DynamicFabExtraButton? = null,
        val dynamicFab: DynamicFab,
    ) : DynamicFabAction {
        override val index: Int = 3000 + dynamicFab.index
    }

    data class Message(
        override val dynamicFabExtraButton: DynamicFabExtraButton? = null,
        val dynamicFabText: DynamicFabText,
    ) : DynamicFabAction {
        override val index: Int = 4000 + dynamicFabText.index
    }

    data class Button(
        override val dynamicFabExtraButton: DynamicFabExtraButton? = null,
        val dynamicFabButton: DynamicFabButton
    ) : DynamicFabAction {
        override val index: Int = 5000 + dynamicFabButton.index
    }

    data class ExtraButton(
        override val dynamicFabExtraButton: DynamicFabExtraButton,
    ) : DynamicFabAction {
        override val index: Int = 6000 + dynamicFabExtraButton.index
    }

}

data class ErrorAction(val amUIError: AmUIError) : MainAction

sealed interface PickerAction : MainAction






