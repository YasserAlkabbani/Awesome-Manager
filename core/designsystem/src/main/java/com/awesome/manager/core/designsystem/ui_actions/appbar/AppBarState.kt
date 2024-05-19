package com.awesome.manager.core.designsystem.ui_actions.appbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface MainAppBar {
    fun showReadAppBar(
        title: String,
        canEdit: Boolean,
        onBack: () -> Unit,
        onEdit: () -> Unit,
        onAddTransaction: (() -> Unit)?
    )

    fun showEditAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit)
    fun showCreateAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit)
    fun showSearchAppBar(syncString: (String) -> Unit)
    fun showMainAppBar(onAddAccount: (() -> Unit)?, onAddTransaction: (() -> Unit)?)
    fun resetAppBar()
    fun AppBarAction.sendAction()
    val appBarAction: StateFlow<AppBarAction>
}

class MainAppBarState : MainAppBar {
    private val _appBarAction: MutableStateFlow<AppBarAction> = MutableStateFlow(AppBarAction.Idle)
    override val appBarAction: StateFlow<AppBarAction> = _appBarAction

    override fun AppBarAction.sendAction() = _appBarAction.update { this }

    override fun resetAppBar() = AppBarAction.Idle.sendAction()

    override fun showMainAppBar(onAddAccount: (() -> Unit)?, onAddTransaction: (() -> Unit)?) =
        AppBarAction.MainNavigation(
            onAddAccount = onAddAccount, onAddTransaction = onAddTransaction
        ).sendAction()

    override fun showSearchAppBar(syncString: (String) -> Unit) =
        AppBarAction.Search(syncString, false).sendAction()

    override fun showCreateAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit) =
        AppBarAction.Create(title = title, onCancel = onCancel, onCreate = onSave).sendAction()

    override fun showEditAppBar(title: String, onCancel: () -> Unit, onSave: () -> Unit) =
        AppBarAction.Edit(title, onCancel, onSave).sendAction()

    override fun showReadAppBar(
        title: String,
        canEdit: Boolean,
        onBack: () -> Unit,
        onEdit: () -> Unit,
        onAddTransaction: (() -> Unit)?
    ) =
        AppBarAction.Read(
            title = title,
            canEdit = canEdit,
            onBack = onBack,
            onEdit = onEdit,
            onAddTransaction = onAddTransaction
        ).sendAction()
}