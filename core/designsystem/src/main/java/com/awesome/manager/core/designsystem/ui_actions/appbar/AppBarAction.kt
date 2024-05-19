package com.awesome.manager.core.designsystem.ui_actions.appbar

import com.awesome.manager.core.designsystem.ui_actions.main.MainAction

sealed class AppBarAction {

    data object Idle : AppBarAction()
    data class MainNavigation(
        val onAddAccount: (() -> Unit)?, val onAddTransaction: (() -> Unit)?
    ) : AppBarAction()

    data class Search(val syncSearchKey: (String) -> Unit, val profile: Boolean) : AppBarAction()
    data class Create(val title: String, val onCancel: () -> Unit, val onCreate: () -> Unit) :
        AppBarAction()

    data class Edit(val title: String, val onCancel: () -> Unit, val onUpdate: () -> Unit) :
        AppBarAction()

    data class Read(
        val title: String, val canEdit: Boolean,
        val onBack: () -> Unit, val onEdit: () -> Unit, val onAddTransaction: (() -> Unit)?
    ) : AppBarAction()

    fun sendMainAction(sendMainAction: (MainAction) -> Unit, resetAppBar: () -> Unit) {
        when (this) {
            Idle -> {}
            else -> {
                resetAppBar()
                sendMainAction(MainAction.AppBar(this))
            }
        }
    }

}