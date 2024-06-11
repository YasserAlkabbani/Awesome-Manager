package com.awesome.manager.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AmSize(val value: Dp) {
    XX_SMALL(16.dp),X_SMALL(20.dp), MID_SMALL(25.dp), SMALL(30.dp), MEDIUM(40.dp), LARGE(50.dp), X_LARGE(60.dp)
}

enum class AmLazyColumnPadding(val value: Dp) {
    SPACE_BETWEEN_ITEM(1.dp), PADDING_BOTTOM(96.dp)
}

enum class AmPadding(val value: Dp) {
    ZERO(0.dp), X_SMALL(2.dp), SMALL(4.dp), MEDIUM(6.dp), LARGE(8.dp), X_LARGE(16.dp)
}