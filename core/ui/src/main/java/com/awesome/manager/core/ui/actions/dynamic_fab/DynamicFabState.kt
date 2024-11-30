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

    fun dynamicFabWelcome() = dynamicFabMessage(
        dynamicFabText = DynamicFabText.WelcomeBack
    )

    fun dynamicFabInvalidEmail() = dynamicFabMessage(
        dynamicFabText = DynamicFabText.InvalidEmail
    )

    fun dynamicFabInvalidPassword() = dynamicFabMessage(
        dynamicFabText = DynamicFabText.InvalidPassword
    )

    fun dynamicFabLogin(login: () -> Unit) = dynamicFabButton(
        dynamicFabButton = DynamicFabButton.Login(
            onClick = login
        )
    )

    fun dynamicFabProfile(showProfileBottomSheet: () -> Unit) = dynamicFab(
        dynamicFab = DynamicFab.Profile(
            onClick = showProfileBottomSheet
        ),
    )

    fun dynamicFabAddAccount(navigateToCreateAccount: () -> Unit) = dynamicFab(
        dynamicFab = DynamicFab.AddAccount(
            onClick = navigateToCreateAccount
        )
    )

    fun dynamicFabAddTransaction(navigateToCreateAccount: () -> Unit) = dynamicFab(
        dynamicFab = DynamicFab.AddTransaction(
            onClick = navigateToCreateAccount
        )
    )

    fun dynamicFabConnectionError(tryAgain: () -> Unit) = dynamicFabMessage(
        dynamicFabText = DynamicFabText.ConnectionError,
        dynamicFabExtraButton = DynamicFabExtraButton.TryAgain(tryAgain)
    )

    fun dynamicFabCertificationError() = dynamicFabMessage(
        dynamicFabText = DynamicFabText.InvalidLoginCredential
    )

    fun dynamicFabInvalidInput(navigatePopBack: () -> Unit) = DynamicFabAction.Message(
        dynamicFabText = DynamicFabText.InvalidInput,
        dynamicFabExtraButton = DynamicFabExtraButton.Back(navigatePopBack)
    ).applyAction()

    fun dynamicFabCreateAccount(onCreate: () -> Unit, navigatePopBack: () -> Unit) =
        DynamicFabAction.Button(
            dynamicFabButton = DynamicFabButton.Create(onCreate),
            dynamicFabExtraButton = DynamicFabExtraButton.Cancel(navigatePopBack)
        ).applyAction()

    fun dynamicFabUpdateAccount(onUpdate: () -> Unit, navigatePopBack: () -> Unit) =
        DynamicFabAction.Button(
            dynamicFabButton = DynamicFabButton.Update(onUpdate),
            dynamicFabExtraButton = DynamicFabExtraButton.Cancel(navigatePopBack)
        ).applyAction()

    fun dynamicFabAddTransaction(
        navigateToCreateTransaction: () -> Unit,
        navigateToEditAccount: (() -> Unit)?,
    ) {
        dynamicFab(
            dynamicFab = DynamicFab.AddTransaction(navigateToCreateTransaction),
            dynamicFabExtraButton = navigateToEditAccount?.let(DynamicFabExtraButton::Edit)
        )
    }

    private fun dynamicFab(
        dynamicFab: DynamicFab,
        dynamicFabExtraButton: DynamicFabExtraButton? = null,
    ) = DynamicFabAction.Fab(
        dynamicFab = dynamicFab,
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()

    private fun dynamicFabButton(
        dynamicFabButton: DynamicFabButton,
        dynamicFabExtraButton: DynamicFabExtraButton? = null,
    ) = DynamicFabAction.Button(
        dynamicFabButton = dynamicFabButton,
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()

    private fun dynamicFabMessage(
        dynamicFabText: DynamicFabText,
        dynamicFabExtraButton: DynamicFabExtraButton? = null,
    ) = DynamicFabAction.Message(
        dynamicFabText = dynamicFabText,
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()


}