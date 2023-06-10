package com.awesome.manager.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeMax
import androidx.compose.material.icons.filled.HomeMini
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.awesome.manager.core.designsystem.R

object AmIcons {
    
    val AwesomeManagerIcon=AmIconsType.DrawableResourceAmIconsType(R.drawable.awesome_manager_icon)

    val HomeSelected= AmIconsType.ImageVictorAmIconsType(Icons.Default.House)
    val HomeUnSelected= AmIconsType.ImageVictorAmIconsType(Icons.Default.Home)
    val HomeAdd=AmIconsType.ImageVictorAmIconsType(Icons.Default.Add)

    val AccountsSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.ManageAccounts)
    val AccountsUnSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.Person)
    val Account=AmIconsType.ImageVictorAmIconsType(Icons.Default.PersonAdd)

    val TransactionsSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.Payments)
    val TransactionsUnSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.CreditCard)
    val TransactionAdd=AmIconsType.ImageVictorAmIconsType(Icons.Default.AddCard)

    val MenuSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.MenuOpen)
    val MenuUnSelected=AmIconsType.ImageVictorAmIconsType(Icons.Default.Menu)

    val ArrowBack=AmIconsType.ImageVictorAmIconsType(Icons.Default.ArrowBack)

}

sealed class AmIconsType{

    data class ImageVictorAmIconsType(val imageVector: ImageVector):AmIconsType()

    data class PainterAmIconsType(val painter: Painter):AmIconsType()

    data class DrawableResourceAmIconsType(@DrawableRes val id:Int):AmIconsType()

}