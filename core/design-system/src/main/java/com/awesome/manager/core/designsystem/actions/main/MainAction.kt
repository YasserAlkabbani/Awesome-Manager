package com.awesome.manager.core.designsystem.actions.main

import com.awesome.manager.core.designsystem.actions.appbar.AppBarButton
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination

sealed class MainAction {
    fun sendMainAction(sendMainAction: (MainAction) -> Unit, doneCurrentAction: () -> Unit) {
        sendMainAction(this)
        doneCurrentAction()
    }
}

sealed class BottomSheetAction : MainAction() {

    data object Dismiss : BottomSheetAction()
    data class Open(
        val content: BottomSheetContent, val isDismissible: Boolean,
    ) : BottomSheetAction()

}

sealed class NavigationAction : MainAction() {

    data object NavigateUp : NavigationAction()
    data class Navigate(val navigationDestination: NavigationDestination) : NavigationAction()

}

data class AppBarAction(
    val visible: Boolean = true,
    val bottomNavigation: Boolean = false,
    val moreButton: Boolean = false,
    val backButton: Boolean = false,
    val cancelButton: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToAccountEditor: NavigationDestination.AccountEditor? = null,
    val navigateToTransactionEditor: NavigationDestination.TransactionEditor? = null,
    val appBarButton: AppBarButton? = null,
) : MainAction()

sealed class PickerAction : MainAction()





