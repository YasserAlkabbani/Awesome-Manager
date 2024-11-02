package com.awesome.manager.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AmSize(val value: Dp) {
    XXXX_SMALL(4.dp),
    XXX_SMALL(6.dp),
    XX_SMALL(12.dp),
    X_SMALL(20.dp),
    SMALL(30.dp),
    MID_SMALL(35.dp),
    MEDIUM(40.dp),
    MID_LARGE(45.dp),
    LARGE(50.dp),
    X_LARGE(54.dp),
    XX_LARGE(64.dp),
    XXX_LARGE(82.dp) ,
    XXXX_LARGE(120.dp)
}

enum class AmLazyColumnPadding(val value: Dp) {
    SPACE_BETWEEN_ITEM(1.dp),
    PADDING_BOTTOM(96.dp)
}

enum class AmPadding(val value: Dp) {
    ZERO(0.dp),
    X_SMALL(2.dp),
    SMALL(4.dp),
    MEDIUM(6.dp),
    LARGE(8.dp),
    X_LARGE(12.dp),
    XX_LARGE(16.dp)
}