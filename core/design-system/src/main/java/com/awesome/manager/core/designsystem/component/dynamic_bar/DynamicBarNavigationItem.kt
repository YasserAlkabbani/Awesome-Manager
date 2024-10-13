package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AmIcon
import com.awesome.manager.core.designsystem.icon.AmIconsType

//@Composable
//fun RowScope.AmDynamicBarNavigationItem(
//    isSelected: Boolean,
//    selectedIcon: AmIconsType,
//    unSelectedIcon: AmIconsType,
//    onSelect: () -> Unit,
//) {
//    Card(
//        modifier = Modifier,
//        shape = MaterialTheme.shapes.extraLarge,
//        colors = CardDefaults.cardColors().copy(
//            contentColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondaryContainer,
//            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
//        ),
//        onClick = onSelect
//    ) {
//        AmIcon(
//            modifier = Modifier
//                .padding(AmPadding.LARGE.value)
//                .size(AmSize.MID_SMALL.value),
//            amIconsType = if (isSelected) selectedIcon else unSelectedIcon,
//        )
//    }
//}