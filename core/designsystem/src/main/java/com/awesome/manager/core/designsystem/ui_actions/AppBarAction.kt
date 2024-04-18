package com.awesome.manager.core.designsystem.ui_actions

sealed class AppBarAction {

    data object Idle : AppBarAction()
    data class MainNavigation(val onAddAccount:(()->Unit)?,val onAddTransaction:(()->Unit)?) : AppBarAction()
    data class Search(val syncSearchKey: (String)->Unit, val profile: Boolean) : AppBarAction()
    data class Create(val title: String, val onCancel: () -> Unit, val onCreate: () -> Unit) :
        AppBarAction()

    data class Edit(val title: String, val onCancel: () -> Unit, val onUpdate: () -> Unit) :
        AppBarAction()

    data class Read(
        val title: String,val canEdit:Boolean,
        val onBack: () -> Unit, val onEdit: () -> Unit,val onAddTransaction:(()->Unit)?
    ) : AppBarAction()

    fun sendAction(sendMainAction:(MainActions)->Unit) {
        sendMainAction(MainActions.AppBar(this))
    }

}