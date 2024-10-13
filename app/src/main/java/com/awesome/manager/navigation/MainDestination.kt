package com.awesome.manager.navigation

import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.ui.actions.main.NavigationAction


enum class MainDestination(
    val navigationDestination: NavigationAction,
    val selectedAmIconsType: AmIconsType,
    val unSelectedAmIconsType: AmIconsType,
) {
    Home(
        navigationDestination = NavigationAction.Home,
        selectedAmIconsType = AmIcons.HomeSelected,
        unSelectedAmIconsType = AmIcons.HomeUnSelected,
    ),
    Accounts(
        navigationDestination = NavigationAction.Accounts,
        selectedAmIconsType = AmIcons.AccountsSelected,
        unSelectedAmIconsType = AmIcons.AccountsUnSelected,
    ),
    Transactions(
        navigationDestination = NavigationAction.Transactions,
        selectedAmIconsType = AmIcons.TransactionsSelected,
        unSelectedAmIconsType = AmIcons.TransactionsUnSelected,
    )
}

fun NavigationAction.isMainDistinction() =
    MainDestination.entries.map { it.navigationDestination }.contains(this)