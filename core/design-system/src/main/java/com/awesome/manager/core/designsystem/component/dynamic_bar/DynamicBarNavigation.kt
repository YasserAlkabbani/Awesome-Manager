package com.awesome.manager.core.designsystem.component.dynamic_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.AmSurface

//@Composable
//fun DynamicBarNavigation(
//    addButton: DynamicBarButtonData?,
//    navigation: @Composable RowScope.() -> Unit,
//    extraButton: DynamicBarButtonData?
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        addButton?.let { addButtonData ->
//            AmDynamicBarExtraButton(
//                dynamicBarButtonData = addButtonData
//            )
//        }
//        AmSurface(
//            modifier = Modifier.padding(AmPadding.X_SMALL.value),
//            shape = MaterialTheme.shapes.extraLarge,
//            padding = AmPadding.ZERO,
//            content = { Row(content = navigation) }
//        )
//        extraButton?.let { extraButtonData ->
//            AmDynamicBarExtraButton(
//                dynamicBarButtonData = extraButtonData
//            )
//        }
//    }
//}