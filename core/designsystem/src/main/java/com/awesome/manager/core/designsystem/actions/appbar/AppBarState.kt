package com.awesome.manager.core.designsystem.actions.appbar

import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface AppBarStateI {

    val appBarAction: StateFlow<AppBarAction?>
    fun AppBarAction.applyAction()
    fun doneAppBarAction()

    fun startLoading()
    fun endLoading()

    fun setForHomeScreen(onAddAccount: () -> Unit, onAddTransaction: () -> Unit)
    fun setForAccountsScreen(onAddAccount: () -> Unit)
    fun setForTransactionsScreen(onAddTransaction: () -> Unit)
    fun setForAccountDetailsScreen(
        onClickBack: () -> Unit, editButtonText: String, allowToEdit: Boolean,
        onEditButton: () -> Unit, onAddTransaction: () -> Unit
    )

    fun setForEditAccountScreen(
        onClickCancel: () -> Unit, saveButtonText: String, onSaveButton: () -> Unit
    )

    fun setForTransactionDetailsScreen(
        onClickBack: () -> Unit, editButtonText: String, allowToEdit: Boolean,
        onEditButton: () -> Unit
    )

    fun setForEditTransactionScreen(
        onClickCancel: () -> Unit, saveButtonText: String, onSaveButton: () -> Unit
    )

}

class AppBarState : AppBarStateI {

    private val _appBarAction: MutableStateFlow<AppBarAction?> = MutableStateFlow(AppBarAction())
    override val appBarAction: StateFlow<AppBarAction?> = _appBarAction

    override fun  AppBarAction.applyAction()=_appBarAction.update { this }
    override fun doneAppBarAction() =_appBarAction.update { null }

    private fun updateAppBarData(
        onAddAccount: (() -> Unit)? = null, onAddTransaction: (() -> Unit)? = null,
        onClickMainButton: (() -> Unit)? = null, onClickBackButton: (() -> Unit)? = null,
        onClickCancelButton: (() -> Unit)? = null, mainButtonText: String = "",
        bottomNavigation: Boolean = false, visible: Boolean = true
    ) = _appBarAction.update {
        it?.copy(
            onAddAccount = onAddAccount, onAddTransaction = onAddTransaction,
            onClickBackButton = onClickBackButton, onClickCancelButton = onClickCancelButton,
            buttonOnClick = onClickMainButton, buttonText = mainButtonText,
            bottomNavigation = bottomNavigation, visible = visible,
        )
    }

    override fun startLoading()=_appBarAction.update { it?.copy(isLoading = true) }
    override fun endLoading()=_appBarAction.update { it?.copy(isLoading = false) }

    override fun setForHomeScreen(onAddAccount: () -> Unit, onAddTransaction: () -> Unit) =
        updateAppBarData(bottomNavigation = true, onAddAccount = onAddTransaction)
    override fun setForAccountsScreen(onAddAccount: () -> Unit) =
        updateAppBarData(onAddAccount = onAddAccount)
    override fun setForTransactionsScreen(onAddTransaction: () -> Unit) =
        updateAppBarData(onAddAccount = onAddTransaction)

    override fun setForAccountDetailsScreen(
        onClickBack: () -> Unit, editButtonText: String,allowToEdit: Boolean,
        onEditButton: () -> Unit, onAddTransaction: () -> Unit
    ) =
        updateAppBarData(
            onClickBackButton = onClickBack, mainButtonText = editButtonText,
            onClickMainButton = if (allowToEdit)onEditButton else null,
            onAddTransaction = onAddTransaction
        )
    override fun setForEditAccountScreen(
        onClickCancel: () -> Unit, saveButtonText: String, onSaveButton: () -> Unit
    ) =
        updateAppBarData(
            onClickBackButton = onClickCancel, mainButtonText = saveButtonText,
            onClickMainButton = onSaveButton
        )

    override fun setForTransactionDetailsScreen(
        onClickBack: () -> Unit, editButtonText: String,allowToEdit: Boolean,
        onEditButton: () -> Unit
    ) =
        updateAppBarData(
            onClickBackButton = onClickBack, mainButtonText = editButtonText,
            onClickMainButton = if (allowToEdit)onEditButton else null,
        )

    override fun setForEditTransactionScreen(
        onClickCancel: () -> Unit, saveButtonText: String, onSaveButton: () -> Unit
    ) =
        updateAppBarData(
            onClickBackButton = onClickCancel, mainButtonText = saveButtonText,
            onClickMainButton = onSaveButton
        )

}

fun AppBarAction?.sendMainAction(sendMainAction: (MainAction) -> Unit, doneAppBarAction: () -> Unit) {
    this?.let {
        doneAppBarAction()
        sendMainAction(this)
    }

}