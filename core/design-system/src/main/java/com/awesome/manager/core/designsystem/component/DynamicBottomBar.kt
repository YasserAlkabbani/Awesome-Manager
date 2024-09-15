package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.actions.main.DynamicBarAction
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AmDynamicBottomBar(
    modifier: Modifier, bottomBarItems: @Composable RowScope.() -> Unit,
    dynamicBarAction: DynamicBarAction
) {
    AmCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        positive = dynamicBarAction.positive,
        padding = AmPadding.ZERO,
    ) {
        AnimatedContent(targetState = dynamicBarAction, label = "APP_BAR") { dynamicBarAction ->
            when (dynamicBarAction) {
                DynamicBarAction.None -> Unit
                DynamicBarAction.Loading -> {
                    AmLinearProgress(
                        modifier = Modifier
                            .padding(AmPadding.XX_LARGE.value)
                            .height(AmSize.XX_SMALL.value)
                            .width(AmSize.XXX_LARGE.value)
                    )
                }

                is DynamicBarAction.BottomNavigation -> {
//                    Surface(color = MaterialTheme.colorScheme.secondary) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        dynamicBarAction.extraButton?.let { (icon, onClick) ->
                            AmActionCustomItem(amIconsType = icon, onClick = onClick)
                        }
                        AmSurface(
                            modifier = Modifier.padding(AmPadding.X_SMALL.value),
                            shape = MaterialTheme.shapes.extraLarge,
                            padding = AmPadding.ZERO,
                            content = { Row(content = bottomBarItems) }
                        )
                        dynamicBarAction.addButton?.let { (icon, onClick) ->
                            AmFloatingActionBottom(amIconsType = icon, onClick = onClick)
                        }
                    }
//                    }
                }

                is DynamicBarAction.Button -> {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AmSurface(
                            modifier = Modifier,
                            padding = AmPadding.X_LARGE,
                            positive = dynamicBarAction.positive,
                            onClick = dynamicBarAction.onClick,
                        ) {
                            Row {
                                AmText(text = dynamicBarAction.text)
                                AmIcon(amIconsType = AmIcons.ArrowForward)
                            }
                        }
                        dynamicBarAction.extraButton?.let { (icon, onClick) ->
                            AmFloatingActionBottom(amIconsType = icon, onClick = onClick)
                        }
                    }
                }

                is DynamicBarAction.Message -> {
                    Row(
                        Modifier.padding(
                            AmPadding.LARGE.value
                        )
                    ) {
                        AmText(
                            modifier = Modifier,
                            text = dynamicBarAction.text,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        dynamicBarAction.extraButton?.let { (icon, onClick) ->
                            AmFloatingActionBottom(
                                amIconsType = icon, onClick = onClick
                            )
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun RowScope.AmNavigationCustomItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    selectedIcon: AmIconsType,
    unSelectedIcon: AmIconsType,
    onSelect: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors().copy(
            contentColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
        ),
        onClick = onSelect
    ) {
        AmIcon(
            modifier = modifier
                .padding(AmPadding.LARGE.value)
                .size(AmSize.MID_SMALL.value),
            amIconsType = if (isSelected) selectedIcon else unSelectedIcon,
        )
    }
}

@Composable
fun RowScope.AmActionCustomItem(
    modifier: Modifier = Modifier,
    amIconsType: AmIconsType,
    onClick: () -> Unit,
) {
    AmSurface(
        modifier = modifier.padding(horizontal = AmPadding.X_SMALL.value),
        padding = AmPadding.X_SMALL,
        shape = MaterialTheme.shapes.extraLarge, onClick = onClick,
    ) {
        AmIcon(
            modifier = Modifier
                .padding(AmPadding.MEDIUM.value)
                .size(AmSize.MID_SMALL.value),
            amIconsType = amIconsType,
        )
    }
}

@Composable
fun AmFloatingActionBottom(amIconsType: AmIconsType, onClick: () -> Unit) {
    SmallFloatingActionButton(
        modifier = Modifier,
        onClick = onClick,
        content = { AmIcon(amIconsType = amIconsType) },
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}