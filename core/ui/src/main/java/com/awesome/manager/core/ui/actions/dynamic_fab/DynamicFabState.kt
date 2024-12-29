package com.awesome.manager.core.ui.actions.dynamic_fab

import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFab
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabExtraButton
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabText
import com.awesome.manager.core.ui.actions.main.DynamicFabAction


interface DynamicFabState {

    fun DynamicFabAction.applyAction()


    ///////// AUTH

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

    ///////// HOME

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

    fun dynamicFabAddTransaction(navigateToCreateTransaction: () -> Unit) = dynamicFab(
        dynamicFab = DynamicFab.AddTransaction(
            onClick = navigateToCreateTransaction
        )
    )

    ///////// ACCOUNT

    fun dynamicFabAccountDetails(
        navigateToCreateTransaction: () -> Unit,
        navigateToEditAccount: () -> Unit,
        hasEditPermission: Boolean
    ) = dynamicFab(
        dynamicFab = DynamicFab.AddTransaction(
            onClick = navigateToCreateTransaction
        ),
        dynamicFabExtraButton = when (hasEditPermission) {
            true -> DynamicFabExtraButton.Edit(onClick = navigateToEditAccount)
            false -> null
        }
    )

    fun dynamicFabCreateAccount(createAccount: () -> Unit, navigatePopBack: () -> Unit) =
        dynamicFabButton(
            dynamicFabButton = DynamicFabButton.Create(createAccount),
            dynamicFabExtraButton = DynamicFabExtraButton.Cancel(navigatePopBack)
        )

    fun dynamicFabUpdateAccount(updateAccount: () -> Unit, navigatePopBack: () -> Unit) =
        dynamicFabButton(
            dynamicFabButton = DynamicFabButton.Update(updateAccount),
            dynamicFabExtraButton = DynamicFabExtraButton.Cancel(navigatePopBack)
        )

    ///////// TRANSACTION

    fun dynamicFabTransactionDetails(
        navigateToTransaction: () -> Unit,
        hasEditTransactionPermission: Boolean
    ) = if (hasEditTransactionPermission) dynamicFabExtraButton(
        dynamicFabExtraButton = DynamicFabExtraButton.Edit(onClick = navigateToTransaction)
    ) else Unit

    fun dynamicFabSearchForAccount(
        searchForAccount: () -> Unit,
        navigatePopBack: () -> Unit
    ) {
        dynamicFabButton(
            dynamicFabButton = DynamicFabButton.SearchForAccount(searchForAccount),
            dynamicFabExtraButton = DynamicFabExtraButton.Back(navigatePopBack),
        )
    }

    fun dynamicFabCreateTransaction(
        createTransaction: () -> Unit,
        navigatePopBack: () -> Unit
    ) {
        dynamicFabButton(
            dynamicFabButton = DynamicFabButton.Create(createTransaction),
            dynamicFabExtraButton = DynamicFabExtraButton.Back(navigatePopBack),
        )
    }

    fun dynamicFabUpdateTransaction(
        updateTransaction: () -> Unit,
        navigatePopBack: () -> Unit
    ) {
        dynamicFabButton(
            dynamicFabButton = DynamicFabButton.Update(updateTransaction),
            dynamicFabExtraButton = DynamicFabExtraButton.Back(navigatePopBack),
        )
    }

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

    private fun dynamicFabExtraButton(
        dynamicFabExtraButton: DynamicFabExtraButton
    ) = DynamicFabAction.ExtraButton(
        dynamicFabExtraButton = dynamicFabExtraButton
    ).applyAction()


}