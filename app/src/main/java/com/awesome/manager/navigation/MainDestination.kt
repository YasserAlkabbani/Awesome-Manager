package com.awesome.manager.navigation

import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.AmTextManager
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.feature.home.R as homeR
import com.awesome.manager.feature.account.accounts.R as accountsR
import com.awesome.manager.feature.transaction.transactions.R as transactionsR


enum class MainDestination(
    val selectedAmIconsType:AmIconsType,
    val unSelectedAmIconsType: AmIconsType,
    val addIcon:AmIconsType,
    val addTitle:AmTextManager,
    val title:AmTextManager,
){

//    Home(
//        selectedAmIconsType=AmIcons.HomeSelected,
//        unSelectedAmIconsType = AmIcons.HomeUnSelected,
//        addIcon = AmIcons.HomeUnSelected,
//        addTitle = "ADD".asAmText(),
//        title = homeR.string.home.asAmText()
//    ),
    Accounts(
        selectedAmIconsType=AmIcons.AccountsSelected,
        unSelectedAmIconsType = AmIcons.AccountsUnSelected,
        addIcon = AmIcons.AccountsUnSelected,
        addTitle = "ADD".asAmText(),
        title = accountsR.string.accounts.asAmText()
    ),
    Transactions(
        selectedAmIconsType=AmIcons.TransactionsSelected,
        unSelectedAmIconsType = AmIcons.TransactionsUnSelected,
        addIcon = AmIcons.TransactionsUnSelected,
        addTitle = "ADD".asAmText(),
        title = transactionsR.string.transactions.asAmText()
    ),

}