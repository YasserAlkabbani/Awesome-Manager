package com.awesome.manager.navigation

import androidx.annotation.StringRes
import com.awesome.manager.R
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.ui.actions.main.NavigationAction


enum class MainDestination(
    val navigationDestination: NavigationAction,
    @StringRes val title:Int,
    val selectedAmIconsType: AmIconsType.ImageVictorAmIconsType,
    val unSelectedAmIconsType: AmIconsType.ImageVictorAmIconsType,
) {
    Home(
        navigationDestination = NavigationAction.Home,
        title = com.awesome.manager.feature.home.R.string.home,
        selectedAmIconsType = AmIcons.HomeSelected,
        unSelectedAmIconsType = AmIcons.HomeUnSelected,
    ),
    Accounts(
        navigationDestination = NavigationAction.Accounts,
        title = com.awesome.manager.feature.account.accounts.R.string.accounts,
        selectedAmIconsType = AmIcons.AccountsSelected,
        unSelectedAmIconsType = AmIcons.AccountsUnSelected,
    ),
    Transactions(
        navigationDestination = NavigationAction.Transactions,
        title = com.awesome.manager.feature.account.details.R.string.transactions,
        selectedAmIconsType = AmIcons.TransactionsSelected,
        unSelectedAmIconsType = AmIcons.TransactionsUnSelected,
    )
}

fun NavigationAction.isMainDistinction() =
    MainDestination.entries.map { it.navigationDestination }.contains(this)