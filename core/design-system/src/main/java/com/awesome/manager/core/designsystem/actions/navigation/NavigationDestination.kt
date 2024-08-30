package com.awesome.manager.core.designsystem.actions.navigation

import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.actions.main.NavigationAction
import kotlinx.serialization.Serializable


@Serializable
sealed class NavigationDestination {

    @Serializable
    data object Intro : NavigationDestination()

    @Serializable
    data object Auth : NavigationDestination()

    @Serializable
    data object Home : NavigationDestination()

    @Serializable
    data object Accounts : NavigationDestination()

    @Serializable
    data object Transactions : NavigationDestination()


    @Serializable
    data class AccountDetails(val accountId: String) : NavigationDestination()

    @Serializable
    data class TransactionDetails(val transactionId: String) : NavigationDestination()


    @Serializable
    data class AccountEditor(val accountId: String?) : NavigationDestination()

    @Serializable
    data class TransactionEditor(val accountId: String?, val transactionId: String?) :
        NavigationDestination()

    fun asNavigation() = NavigationAction.Navigate(this)
    fun isMainDistinction() =
        MainDistillation.entries.map { it.navigationDestination }.contains(this)

}

enum class MainDistillation(
    val navigationDestination: NavigationDestination,
    val selectedAmIconsType: AmIconsType,
    val unSelectedAmIconsType: AmIconsType,
) {
    Home(
        navigationDestination = NavigationDestination.Home,
        selectedAmIconsType = AmIcons.HomeSelected,
        unSelectedAmIconsType = AmIcons.HomeUnSelected,
    ),
    Accounts(
        navigationDestination = NavigationDestination.Accounts,
        selectedAmIconsType = AmIcons.AccountsSelected,
        unSelectedAmIconsType = AmIcons.AccountsUnSelected,
    ),
    Transactions(
        navigationDestination = NavigationDestination.Transactions,
        selectedAmIconsType = AmIcons.TransactionsSelected,
        unSelectedAmIconsType = AmIcons.TransactionsUnSelected,
    )
}