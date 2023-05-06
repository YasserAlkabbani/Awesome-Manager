package com.awesome.manager.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeMax
import androidx.compose.material.icons.filled.HomeMini
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.ui.graphics.vector.ImageVector

object MaIcon {

    val HomeSelected= Icons.Default.HomeMax
    val HomeUnSelected= Icons.Default.HomeMini

    val AccountsSelected=Icons.Default.AccountCircle
    val AccountsUnSelected=Icons.Default.ManageAccounts

    val TransactionsSelected=Icons.Default.Money
    val TransactionsUnSelected=Icons.Default.MoneyOff

    val MenuSelected=Icons.Default.MenuOpen
    val MenuUnSelected=Icons.Default.Menu

}

sealed class Icon{

    data class ImageVictorIcon(val imageVector: ImageVector):Icon()

    data class DrawableResourceIcon(@DrawableRes val id:Int):Icon()

}