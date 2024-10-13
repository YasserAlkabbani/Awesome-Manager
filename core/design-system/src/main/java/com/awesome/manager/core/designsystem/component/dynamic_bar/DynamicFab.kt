package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType


@Composable
fun AmDynamicFab(dynamicFab:DynamicFab) {
    SmallFloatingActionButton(
        modifier = Modifier,
        onClick = dynamicFab.onClick,
        content = { AmIcon(amIconsType = dynamicFab.amIconsType) },
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}


sealed class DynamicFab(
    val isPositive: Boolean, val amIconsType: AmIconsType,
) {
    abstract val onClick: () -> Unit

    data class AddAccount(override val onClick: () -> Unit) :
        DynamicFab(isPositive = true, amIconsType = AmIcons.AccountAdd)

    data class AddTransaction(override val onClick: () -> Unit) :
        DynamicFab(isPositive = true, amIconsType = AmIcons.TransactionAdd)
}