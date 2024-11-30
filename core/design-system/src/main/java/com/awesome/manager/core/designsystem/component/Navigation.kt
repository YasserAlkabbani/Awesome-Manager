package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmNavigationBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { with(density) { -40.dp.roundToPx() } } +
                expandVertically(expandFrom = Alignment.Top) +
                fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        NavigationBar(
            modifier = modifier.height(AmSize.XXX_LARGE.value),
            content = content,
        )
    }

}


@Composable
fun RowScope.AmNavigationItem(
    isSelected: Boolean,
    selectedIcon: AmIconsType.ImageVictorAmIconsType,
    unSelectedIcon: AmIconsType.ImageVictorAmIconsType,
    title: String,
    onSelect: () -> Unit,
) {
    NavigationBarItem(
        modifier = Modifier,
        selected = isSelected,
        icon = { AmIcon(amIconsType = if (isSelected) selectedIcon else unSelectedIcon) },
        onClick = onSelect,
        label = { AmText(text = title) }
    )
}