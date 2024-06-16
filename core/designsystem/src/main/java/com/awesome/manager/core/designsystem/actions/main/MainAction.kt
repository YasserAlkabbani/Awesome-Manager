package com.awesome.manager.core.designsystem.actions.main

import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination

sealed class MainAction

sealed class BottomSheetAction : MainAction() {

    abstract val content: BottomSheetContent?
    abstract val isDismissible: Boolean

    data class Dismiss(
        override val content: BottomSheetContent?,
        override val isDismissible: Boolean = false,
    ) : BottomSheetAction()

    data class Open(
        override val content: BottomSheetContent,
        override val isDismissible: Boolean = false,
    ) : BottomSheetAction()

}

sealed class NavigationAction : MainAction() {

    data object NavigateUp : NavigationAction()
    data class Navigate(val navigationDestination: NavigationDestination) : NavigationAction()

}

data class AppBarAction(
    val isLoading: Boolean = false,
    val visible: Boolean = true,
    val bottomNavigation: Boolean = false,
    val errorMessage: String? = null,
    val buttonText: String = "",
    val buttonOnClick: (() -> Unit)? = null,
    val onAddAccount: (() -> Unit)? = null,
    val onAddTransaction: (() -> Unit)? = null,
    val onClickBackButton: (() -> Unit)? = null,
    val onClickCancelButton: (() -> Unit)? = null,
) : MainAction()

sealed class PickerAction : MainAction() {


}





