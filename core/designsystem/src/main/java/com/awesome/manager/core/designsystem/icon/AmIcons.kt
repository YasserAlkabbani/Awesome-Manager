package com.awesome.manager.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.DomainAdd
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Input
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.Domain
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.awesome.manager.core.designsystem.R

object AmIcons {

    val AwesomeManagerIcon =
        AmIconsType.DrawableResourceAmIconsType(R.drawable.awesome_manager_icon)

    val PlaceHolderIcon = AmIconsType.DrawableResourceAmIconsType(R.drawable.awesome_manager_icon)

    val HomeSelected = AmIconsType.ImageVictorAmIconsType(Icons.Default.House)
    val HomeUnSelected = AmIconsType.ImageVictorAmIconsType(Icons.Outlined.House)
    val HomeAdd = AmIconsType.ImageVictorAmIconsType(Icons.Default.Add)

    val AccountsSelected = AmIconsType.ImageVictorAmIconsType(Icons.Default.AccountTree)
    val AccountsUnSelected = AmIconsType.ImageVictorAmIconsType(Icons.Outlined.AccountTree)
    val AccountAdd = AmIconsType.ImageVictorAmIconsType(Icons.Default.DomainAdd)

    val TransactionsSelected = AmIconsType.ImageVictorAmIconsType(Icons.Default.Payments)
    val TransactionsUnSelected = AmIconsType.ImageVictorAmIconsType(Icons.Outlined.Payments)
    val TransactionAdd = AmIconsType.ImageVictorAmIconsType(Icons.Default.AddCard)

    val MenuSelected = AmIconsType.ImageVictorAmIconsType(Icons.AutoMirrored.Filled.MenuOpen)
    val MenuUnSelected = AmIconsType.ImageVictorAmIconsType(Icons.Default.Menu)

    val ArrowBack = AmIconsType.ImageVictorAmIconsType(Icons.Default.ArrowBackIosNew)
    val ArrowForward = AmIconsType.ImageVictorAmIconsType(Icons.AutoMirrored.Filled.ArrowForwardIos)
    val Edit = AmIconsType.ImageVictorAmIconsType(Icons.Default.Edit)
    val Save = AmIconsType.ImageVictorAmIconsType(Icons.Default.Save)
    val Close = AmIconsType.ImageVictorAmIconsType(Icons.Default.Close)
    val More = AmIconsType.ImageVictorAmIconsType(Icons.Default.MoreHoriz)

    val Email = AmIconsType.ImageVictorAmIconsType(Icons.Default.Email)
    val Password = AmIconsType.ImageVictorAmIconsType(Icons.Default.Password)

    val Title = AmIconsType.ImageVictorAmIconsType(Icons.Default.Title)
    val SubTitle = AmIconsType.ImageVictorAmIconsType(Icons.Default.Subtitles)
    val Money = AmIconsType.ImageVictorAmIconsType(Icons.Default.Money)

    val Done = AmIconsType.ImageVictorAmIconsType(Icons.Default.Done)

    val Search = AmIconsType.ImageVictorAmIconsType(Icons.Default.Search)

    val Error = AmIconsType.ImageVictorAmIconsType(Icons.Default.Error)

    val Visibility = AmIconsType.ImageVictorAmIconsType(Icons.Filled.Visibility)
    val VisibilityOff = AmIconsType.ImageVictorAmIconsType(Icons.Filled.VisibilityOff)

    val Input = AmIconsType.ImageVictorAmIconsType(Icons.AutoMirrored.Filled.Input)
    val Output = AmIconsType.ImageVictorAmIconsType(Icons.Filled.Output)
    val Balance = AmIconsType.ImageVictorAmIconsType(Icons.Filled.Balance)

    val Date = AmIconsType.ImageVictorAmIconsType(Icons.Filled.DateRange)
    val Category = AmIconsType.ImageVictorAmIconsType(Icons.Filled.Category)
    val Profile = AmIconsType.ImageVictorAmIconsType(Icons.Filled.AccountCircle)

}

sealed class AmIconsType {

    data class ImageVictorAmIconsType(val imageVector: ImageVector) : AmIconsType()

    data class PainterAmIconsType(val painter: Painter) : AmIconsType()

    data class DrawableResourceAmIconsType(@DrawableRes val id: Int) : AmIconsType()

}