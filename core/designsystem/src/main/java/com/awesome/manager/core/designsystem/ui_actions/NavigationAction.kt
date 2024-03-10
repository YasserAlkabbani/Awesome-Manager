package com.awesome.manager.core.designsystem.ui_actions

sealed class NavigationAction {

    data object Idle : NavigationAction()
    data object PopBack : NavigationAction()

    data object Intro : NavigationAction()
    data object Auth : NavigationAction()
    data object Home : NavigationAction()

    data object CreateAccount : NavigationAction()
    data class CreateTransaction(val accountId: String) : NavigationAction()

    data class ReadAccount(val accountId: String) : NavigationAction()
    data class ReadTransaction(val transactionId: String) : NavigationAction()

    data class EditAccount(val accountId: String) : NavigationAction()
    data class EditTransaction(val transactionId: String) : NavigationAction()

    fun sendAction(sendMainAction:(MainActions)->Unit, resetNavigation:()->Unit) {
        when (this) {
            Idle -> {}
            else -> {
                resetNavigation()
                sendMainAction(MainActions.Navigate(this))
            }
        }
    }

}