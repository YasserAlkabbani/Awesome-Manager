package com.awesome.manager.navigation

import androidx.annotation.StringRes
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.feature.account.accounts.AccountsRoute
import com.awesome.manager.feature.home.HomeRoute
import com.awesome.manager.feature.transaction.transactions.TransactionRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


enum class MainDestination(
    @StringRes val title: Int,
    val selectedAmIconsType: AmIconsType.ImageVictorAmIconsType,
    val unSelectedAmIconsType: AmIconsType.ImageVictorAmIconsType,
    val route: KClass<*>
) {
    @Serializable
    Home(
        title = com.awesome.manager.feature.home.R.string.home,
        selectedAmIconsType = AmIcons.HomeSelected,
        unSelectedAmIconsType = AmIcons.HomeUnSelected,
        route = HomeRoute::class
    ),

    @Serializable
    Accounts(
        title = com.awesome.manager.feature.account.accounts.R.string.accounts,
        selectedAmIconsType = AmIcons.AccountsSelected,
        unSelectedAmIconsType = AmIcons.AccountsUnSelected,
        route = AccountsRoute::class
    ),

    @Serializable
    Transactions(
        title = com.awesome.manager.feature.account.details.R.string.transactions,
        selectedAmIconsType = AmIcons.TransactionsSelected,
        unSelectedAmIconsType = AmIcons.TransactionsUnSelected,
        route = TransactionRoute::class
    )

}