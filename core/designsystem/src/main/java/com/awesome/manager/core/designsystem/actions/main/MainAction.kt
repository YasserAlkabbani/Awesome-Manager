package com.awesome.manager.core.designsystem.actions.main

import androidx.compose.foundation.lazy.LazyListScope
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination

sealed class MainAction

sealed class BottomSheetAction(open val isDismissible: Boolean = false) : MainAction() {

    data class Dismiss<T : BottomSheetAction>(val bottomSheet: T?) : BottomSheetAction()

    data class Profile(
        val email: String,
        val logout: () -> Unit
    ) : BottomSheetAction()

    data class SearchForAccount(
        val items: LazyListScope.() -> Unit,
        val onReSearch: (String) -> Unit
    ) : BottomSheetAction()

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

}

sealed class NavigationAction : MainAction() {

    data object PopBack : NavigationAction()
    data class Navigate(val navigationDestination: NavigationDestination) : NavigationAction()

}

data class AppBarAction(
    val onAddAccount: (() -> Unit)? = null,
    val onAddTransaction: (() -> Unit)? = null,
    val onClickBackButton: (() -> Unit)? = null,
    val onClickCancelButton: (() -> Unit)? = null,
    val buttonOnClick: (() -> Unit)? = null,
    val buttonText: String = "",
    val bottomNavigation: Boolean = false,
    val isLoading: Boolean = false,
    val visible: Boolean = true
) : MainAction()

sealed class PickerAction : MainAction() {

    data object Hide : PickerAction()

    data class PickDate(
        val initTime: Long, val setDate: (Long) -> Unit, val dismiss: () -> Unit
    ) : PickerAction()

}





