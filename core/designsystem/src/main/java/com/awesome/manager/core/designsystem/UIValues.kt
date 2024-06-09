package com.awesome.manager.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AmSize(val value: Dp) {
    EXTRA_SMALL(20.dp), MID_SMALL(25.dp), SMALL(30.dp), MEDIUM(40.dp), LARGE(50.dp), EXTRA_LARGE(60.dp)
}

enum class AmLazyColumnPadding(val value: Dp) {
    SPACE_BETWEEN_ITEM(1.dp), PADDING_BOTTOM(96.dp)
}

enum class AmPadding(val value: Dp) {
    ZERO(0.dp), EXTRA_SMALL(2.dp), SMALL(4.dp), MEDIUM(6.dp), LARGE(8.dp), EXTRA_LARGE(16.dp)
}