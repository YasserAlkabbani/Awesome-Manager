package com.awesome.manager.core.designsystem.actions.appbar

import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface AppBarStateI {

    val appBarAction: StateFlow<AppBarAction?>
    fun AppBarAction.applyAction()
    fun doneAppBarAction()

    fun setForHomeScreen(
        onAddAccount: () -> Unit, onAddTransaction: () -> Unit,
    )

    fun setForAccountsScreen(
        onAddAccount: () -> Unit,
    )

    fun setForTransactionsScreen(
        onAddTransaction: () -> Unit,
    )

    fun setForAccountDetailsScreen(
        editButtonText: String, allowToEdit: Boolean, onEditButton: () -> Unit,
        onClickBack: () -> Unit, onAddTransaction: () -> Unit
    )

    fun setForEditAccountScreen(
        errorMessage: String?,
        saveButtonText: String, onSaveButton: (() -> Unit)?,
        onClickCancel: () -> Unit
    )

    fun setForTransactionDetailsScreen(
        editButtonText: String, allowToEdit: Boolean, onEditButton: () -> Unit,
        onClickBack: () -> Unit
    )

    fun setForEditTransactionScreen(
        errorMessage: String?,
        saveButtonText: String, onSaveButton: (() -> Unit)?, onClickCancel: () -> Unit
    )

}

class AppBarState : AppBarStateI {

    private val _appBarAction: MutableStateFlow<AppBarAction?> = MutableStateFlow(null)
    override val appBarAction: StateFlow<AppBarAction?> = _appBarAction.asStateFlow()

    override fun AppBarAction.applyAction() = _appBarAction.update { this }
    override fun doneAppBarAction() = _appBarAction.update { null }

    private fun updateAppBarData(
        isLoading: Boolean = false, visible: Boolean = true,
        bottomNavigation: Boolean = false, errorMessage: String? = null,
        buttonText: String = "", buttonOnClick: (() -> Unit)? = null,
        onAddAccount: (() -> Unit)? = null, onAddTransaction: (() -> Unit)? = null,
        onClickBackButton: (() -> Unit)? = null, onClickCancelButton: (() -> Unit)? = null,
    ) = _appBarAction.update {
        it?.copy(
            onAddAccount = onAddAccount, onAddTransaction = onAddTransaction,
            bottomNavigation = bottomNavigation, visible = visible, isLoading = isLoading,
            onClickBackButton = onClickBackButton, onClickCancelButton = onClickCancelButton,
            buttonOnClick = buttonOnClick, buttonText = buttonText,
            errorMessage = errorMessage
        ) ?: AppBarAction(
            onAddAccount = onAddAccount, onAddTransaction = onAddTransaction,
            bottomNavigation = bottomNavigation, visible = visible, isLoading = isLoading,
            onClickBackButton = onClickBackButton, onClickCancelButton = onClickCancelButton,
            buttonOnClick = buttonOnClick, buttonText = buttonText,
            errorMessage = errorMessage
        )
    }

    override fun setForHomeScreen(
        onAddAccount: () -> Unit, onAddTransaction: () -> Unit,
    ) =
        updateAppBarData(
            bottomNavigation = true,
            onAddAccount = onAddAccount, onAddTransaction = onAddTransaction
        )

    override fun setForAccountsScreen(
        onAddAccount: () -> Unit,
    ) =
        updateAppBarData(bottomNavigation = true, onAddAccount = onAddAccount)

    override fun setForTransactionsScreen(onAddTransaction: () -> Unit) =
        updateAppBarData(bottomNavigation = true, onAddTransaction = onAddTransaction)

    override fun setForAccountDetailsScreen(
        editButtonText: String, allowToEdit: Boolean, onEditButton: () -> Unit,
        onClickBack: () -> Unit, onAddTransaction: () -> Unit
    ) =
        updateAppBarData(
            onClickBackButton = onClickBack,
            buttonText = editButtonText, buttonOnClick = if (allowToEdit) onEditButton else null,
            onAddTransaction = onAddTransaction
        )

    override fun setForEditAccountScreen(
        errorMessage: String?,
        saveButtonText: String, onSaveButton: (() -> Unit)?,
        onClickCancel: () -> Unit
    ) =
        updateAppBarData(
            errorMessage = errorMessage,
            buttonText = saveButtonText, buttonOnClick = onSaveButton,
            onClickBackButton = onClickCancel,
        )

    override fun setForTransactionDetailsScreen(
        editButtonText: String, allowToEdit: Boolean, onEditButton: () -> Unit,
        onClickBack: () -> Unit
    ) =
        updateAppBarData(
            buttonText = editButtonText,
            onClickBackButton = if (allowToEdit) onEditButton else null,
            buttonOnClick = onClickBack,
        )

    override fun setForEditTransactionScreen(
        errorMessage: String?,
        saveButtonText: String, onSaveButton: (() -> Unit)?, onClickCancel: () -> Unit
    ) =
        updateAppBarData(
            errorMessage = errorMessage,
            buttonText = saveButtonText,
            buttonOnClick = onSaveButton,
            onClickCancelButton = onClickCancel
        )

}

fun AppBarAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit,
    doneAppBarAction: () -> Unit
) {
    this?.let {
        doneAppBarAction()
        sendMainAction(this)
    }

}