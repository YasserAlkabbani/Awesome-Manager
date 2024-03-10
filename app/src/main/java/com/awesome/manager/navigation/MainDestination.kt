package com.awesome.manager.navigation

import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.AmTextManager
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.feature.home.R as homeR
import com.awesome.manager.feature.account.accounts.R as accountsR
import com.awesome.manager.feature.transaction.transactions.R as transactionsR

data class AddButton(val title: AmTextManager, val icon: AmIconsType)

enum class MainDestination(
    val selectedAmIconsType: AmIconsType,
    val unSelectedAmIconsType: AmIconsType,
    val addButton: AddButton,
    val title: AmTextManager,
) {
    Home(
        selectedAmIconsType = AmIcons.HomeSelected,
        unSelectedAmIconsType = AmIcons.HomeUnSelected,
        addButton = AddButton(title = "Create Account".asAmText(), icon = AmIcons.HomeAdd),
        title = homeR.string.home.asAmText()
    ),
    Accounts(
        selectedAmIconsType = AmIcons.AccountsSelected,
        unSelectedAmIconsType = AmIcons.AccountsUnSelected,
        addButton = AddButton(title = "Create Account".asAmText(), icon = AmIcons.AccountAdd),
        title = accountsR.string.accounts.asAmText()
    ),
    Transactions(
        selectedAmIconsType = AmIcons.TransactionsSelected,
        unSelectedAmIconsType = AmIcons.TransactionsUnSelected,
        addButton = AddButton(
            title = "Create Transaction".asAmText(),
            icon = AmIcons.TransactionAdd
        ),
        title = transactionsR.string.transactions.asAmText()
    ),
//    Menu(
//        selectedAmIconsType = AmIcons.MenuSelected,
//        unSelectedAmIconsType = AmIcons.MenuUnSelected,
//        addButton = null,
//        title = menuR.string.menu.asAmText()
//    ),

}