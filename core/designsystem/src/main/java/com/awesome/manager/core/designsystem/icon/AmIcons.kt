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
import com.awesome.manager.core.designsystem.R

object AmIcons {
    
    val AwesomeManagerIcon=AmIconsType.DrawableResourceAmIconsType(R.drawable.awesome_manager_icon)

    val HomeSelected= AmIconsType.ImageVictorAmIconsType(Icons.Default.HomeMax)
    val HomeUnSelected= AmIconsType.ImageVictorAmIconsType(Icons.Default.HomeMini)

    val AccountsSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.AccountCircle)
    val AccountsUnSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.ManageAccounts)

    val TransactionsSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.Money)
    val TransactionsUnSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.MoneyOff)

    val MenuSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.MenuOpen)
    val MenuUnSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.Menu)

    val ArrowBack=AmIconsType.ImageVictorAmIconsType(Icons.Default.ArrowBack)

}

sealed class AmIconsType{

    data class ImageVictorAmIconsType(val imageVector: ImageVector):AmIconsType()

    data class PainterAmIconsType(val painter: Painter):AmIconsType()

    data class DrawableResourceAmIconsType(@DrawableRes val id:Int):AmIconsType()

}