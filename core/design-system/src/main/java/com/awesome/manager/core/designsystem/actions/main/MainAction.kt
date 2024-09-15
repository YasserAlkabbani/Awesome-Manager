package com.awesome.manager.core.designsystem.actions.main

import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetContent
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import com.awesome.manager.core.designsystem.icon.AmIconsType

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

sealed class DynamicBarAction : MainAction() {

    abstract val positive: Boolean

    data object None : DynamicBarAction() {
        override val positive: Boolean = true
    }

    data object Loading : DynamicBarAction() {
        override val positive: Boolean = true
    }

    data class BottomNavigation(val extraButton: ExtraButton?, val addButton: ExtraButton?) :
        DynamicBarAction() {
        override val positive: Boolean = true
    }

    data class Message(
        val text: String, override val positive: Boolean, val extraButton: ExtraButton?
    ) : DynamicBarAction()

    data class Button(
        val text: String, val onClick: () -> Unit, override val positive: Boolean,
        val extraButton: ExtraButton?
    ) : DynamicBarAction()


    data class ExtraButton(val amIconsType: AmIconsType, val onClick: () -> Unit)
}

sealed class PickerAction : MainAction()





