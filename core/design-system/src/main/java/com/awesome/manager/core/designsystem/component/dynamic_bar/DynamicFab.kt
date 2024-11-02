package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmDynamicFab(dynamicFab:DynamicFab) {
//    val positiveColor=MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.secondaryContainer
//    val negativeColor=MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.secondaryContainer
//    val color= remember(dynamicFab.isPositive) {
//        when(dynamicFab.isPositive){
//            true -> positiveColor
//            false -> negativeColor
//        }
//    }
    FloatingActionButton(
        modifier = Modifier,
        onClick = dynamicFab.onClick,
        content = { AmIcon(amIconsType = dynamicFab.amIconsType) },
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        shape = MaterialTheme.shapes.extraLarge,
    )
}


sealed class DynamicFab(
    val isPositive: Boolean, val amIconsType: AmIconsType,val index:Int,
) {
    abstract val onClick: () -> Unit

    data class Profile(override val onClick: () -> Unit) :
        DynamicFab(isPositive = true, amIconsType = AmIcons.Profile, index = 0)

    data class AddAccount(override val onClick: () -> Unit) :
        DynamicFab(isPositive = true, amIconsType = AmIcons.AccountAdd, index = 1)

    data class AddTransaction(override val onClick: () -> Unit) :
        DynamicFab(isPositive = true, amIconsType = AmIcons.TransactionAdd, index = 2)
}