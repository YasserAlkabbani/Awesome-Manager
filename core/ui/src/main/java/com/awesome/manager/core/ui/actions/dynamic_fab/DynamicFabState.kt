package com.awesome.manager.core.ui.actions.dynamic_fab

import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFab
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import com.awesome.manager.core.ui.actions.main.DynamicFabAction


interface DynamicFabState {

    fun DynamicFabAction.applyAction()

    fun dynamicFabLoading() =
        DynamicFabAction.Loading.applyAction()

    fun dynamicFab(
        dynamicFab: DynamicFab,
        dynamicFabExtraButton: DynamicFabExtraButton? = null
    ) = DynamicFabAction.Fab(
        dynamicFab = dynamicFab,
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()

    fun dynamicFabButton(
        dynamicFabButton: DynamicFabButton,
        dynamicFabExtraButton: DynamicFabExtraButton? = null
    ) = DynamicFabAction.Button(
        dynamicFabButton = dynamicFabButton,
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()

    fun dynamicFabMessage(
        dynamicFabText: DynamicFabText,
        dynamicFabExtraButton: DynamicFabExtraButton? = null
    ) = DynamicFabAction.Message(
        dynamicFabText = dynamicFabText,
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()


}