package com.awesome.manager.core.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AmSize(val value: Dp) {
    XXXX_SMALL(2.dp),
    XX_SMALL(12.dp),
    X_SMALL(20.dp),
    SMALL(30.dp),
    MEDIUM(40.dp),
    LARGE(50.dp),
    XX_LARGE(64.dp),
    XXX_LARGE(82.dp) ,
    XXXX_LARGE(120.dp),
    LOADING_INDICATOR_HEIGHT(10.dp)
}

enum class AmLazyColumnPadding(val value: Dp) {
    SPACE_BETWEEN_ITEM(1.dp),
    PADDING_BOTTOM(96.dp)
}

enum class AmPadding(val value: Dp) {
    ZERO(0.dp),
    XX_SMALL(2.dp),
    CARD_SURFACE_INDICATOR(3.dp),
    SMALL(4.dp),
    MEDIUM(6.dp),
    LARGE(8.dp),
    Details(24.dp),
    XX_LARGE(16.dp),
    BUTTON(10.dp),
}