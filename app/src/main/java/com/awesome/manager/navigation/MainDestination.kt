package com.awesome.manager.navigation

import com.awesome.manager.core.designsystem.icon.MaIconsType
import com.awesome.manager.core.designsystem.icon.MaIcons
import com.awesome.manager.feature.home.R as homeR
import com.awesome.manager.feature.accounts.R as accountsR
import com.awesome.manager.feature.transactions.R as transactionsR


enum class MainDestination(
    val selectedMaIconsType:MaIconsType,
    val unSelectedMaIconsType: MaIconsType,
    val titleTextId:Int,
){

    Home(
        selectedMaIconsType=MaIcons.HomeSelected,
        unSelectedMaIconsType = MaIcons.HomeUnSelected,
        titleTextId = homeR.string.home
    ),
    Accounts(
        selectedMaIconsType=MaIcons.AccountsSelected,
        unSelectedMaIconsType = MaIcons.AccountsUnSelected,
        titleTextId = accountsR.string.accounts
    ),
    Transactions(
        selectedMaIconsType=MaIcons.TransactionsSelected,
        unSelectedMaIconsType = MaIcons.TransactionsUnSelected,
        titleTextId = transactionsR.string.transactions
    ),

}