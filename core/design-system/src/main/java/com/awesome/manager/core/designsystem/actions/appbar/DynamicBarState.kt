package com.awesome.manager.core.designsystem.actions.appbar

import com.awesome.manager.core.designsystem.actions.main.DynamicBarAction


interface DynamicBarState {

    fun DynamicBarAction.applyAction()

    fun dynamicBarLoading() = DynamicBarAction.Loading.applyAction()

    fun dynamicBarMessage(
        text: String,
        positive: Boolean,
        extraButton: DynamicBarAction.ExtraButton?
    ) = DynamicBarAction.Message(text, positive, extraButton).applyAction()

    fun dynamicBarButton(
        text: String, onClick: () -> Unit, positive: Boolean,
        extraButton: DynamicBarAction.ExtraButton?
    ) = DynamicBarAction.Button(text, onClick, positive, extraButton).applyAction()

    fun dynamicBarNavigation(
        extraButton: DynamicBarAction.ExtraButton?,
        addButton: DynamicBarAction.ExtraButton?
    ) = DynamicBarAction
        .BottomNavigation(extraButton = extraButton, addButton = addButton).applyAction()

}