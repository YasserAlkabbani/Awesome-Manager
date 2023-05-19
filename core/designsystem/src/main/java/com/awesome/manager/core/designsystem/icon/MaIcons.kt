package com.awesome.manager.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HomeMax
import androidx.compose.material.icons.filled.HomeMini
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

object MaIcons {

    val HomeSelected= MaIconsType.ImageVictorMaIconsType(Icons.Default.HomeMax)
    val HomeUnSelected= MaIconsType.ImageVictorMaIconsType(Icons.Default.HomeMini)

    val AccountsSelected=MaIconsType.ImageVictorMaIconsType(Icons.Default.AccountCircle)
    val AccountsUnSelected=MaIconsType.ImageVictorMaIconsType(Icons.Default.ManageAccounts)

    val TransactionsSelected=MaIconsType.ImageVictorMaIconsType(Icons.Default.Money)
    val TransactionsUnSelected=MaIconsType.ImageVictorMaIconsType(Icons.Default.MoneyOff)

    val MenuSelected=MaIconsType.ImageVictorMaIconsType(Icons.Default.MenuOpen)
    val MenuUnSelected=MaIconsType.ImageVictorMaIconsType(Icons.Default.Menu)

    val ArrowBack=MaIconsType.ImageVictorMaIconsType(Icons.Default.ArrowBack)

}

sealed class MaIconsType{

    data class ImageVictorMaIconsType(val imageVector: ImageVector):MaIconsType()

    data class PainterMaIconsType(val painter: Painter):MaIconsType()

    data class DrawableResourceMaIconsType(@DrawableRes val id:Int):MaIconsType()

}