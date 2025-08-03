package com.awesome.manager.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AmSize(val value: Dp) {
    TEXT_FIELD_MINIMUM_HEIGHT(42.dp),
    XX_SMALL(12.dp),
    X_SMALL(20.dp),
    SMALL(30.dp),
    MEDIUM(40.dp),
    LARGE(50.dp),
    ACCOUNT_IMAGE_SIZE(50.dp),
    LARGE_IMAGE_SIZE(82.dp),
    BOTTOM_NAVIGATION_HEIGHT(90.dp),
    FLOATING_TOOLBAR_LOADING_HEIGHT(12.dp),
    FLOATING_TOOLBAR_LOADING_WIDTH(120.dp),
    CARD_LOADING_SIZE(24.dp)
}

enum class AmLazyColumnPadding(val value: Dp) {
    SPACE_BETWEEN_ITEM(1.dp),
    PADDING_BOTTOM(96.dp)
}

enum class AmPadding(val value: Dp) {
    ZERO(0.dp),
    XX_SMALL(2.dp),
    CARD_SURFACE_INDICATOR(6.dp),
    SMALL(4.dp),
    COULMN_ITEMS_PADDING(12.dp),
    LARGE_IMAGE_PADDING(16.dp),
    HORIZONTAL_PADDING(8.dp),
    CARD_PADDING_SMALL(4.dp),
    CARD_PADDING_MEDIUM(8.dp),
    TEXT_HORIZONTAL_PADDING(8.dp),
    CARD_PADDING_LARGE(12.dp),
    Details(24.dp),
    XX_LARGE(16.dp),
    AM_LOGO_SIZE(100.dp),
    AUTH_SCREEN(16.dp),
    AUTH_TEXT_FAILED(16.dp),
    BUTTON(12.dp),
    UNDER_LOGO(80.dp),
    BETWEEN_TEXT_FILED(8.dp),
    HORIZONTAL_FLOATING_TOOLBAR(16.dp)
}

@Composable
fun Boolean?.getColors() = when (this) {
    true -> MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.primaryContainer
    false -> MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.errorContainer
    null -> MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.secondaryContainer
}
