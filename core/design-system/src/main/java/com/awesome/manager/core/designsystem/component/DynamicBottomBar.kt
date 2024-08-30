package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.component.text.AmText

@Composable
fun AmDynamicBottomBar(
    modifier: Modifier,
    bottomBarItems: @Composable RowScope.() -> Unit,
    onNavigationUp: () -> Unit, onShowMoreBottomSheet: () -> Unit,
    accountEditor: (NavigationDestination.AccountEditor) -> Unit,
    transactionEditor: (NavigationDestination.TransactionEditor) -> Unit,
    appBarAction: AppBarAction
) {
    AnimatedVisibility(appBarAction.visible) {
        Row(
            modifier = Modifier
                .height(AmSize.X_LARGE.value)
                .animateContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(!appBarAction.isLoading) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(AmPadding.X_SMALL.value),
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxHeight()
                            .padding(AmPadding.X_SMALL.value),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AnimatedVisibility(appBarAction.backButton) {
                            AmActionCustomItem(
                                amIconsType = AmIcons.ArrowBack,
                                onClick = onNavigationUp
                            )
                        }
                        AnimatedVisibility(appBarAction.cancelButton) {
                            AmActionCustomItem(
                                amIconsType = AmIcons.Close,
                                onClick = onNavigationUp
                            )
                        }
                        AnimatedVisibility(appBarAction.moreButton) {
                            AmActionCustomItem(
                                amIconsType = AmIcons.More,
                                onClick = onShowMoreBottomSheet
                            )
                        }
                        AnimatedVisibility(appBarAction.bottomNavigation) {
                            AmSurface(
                                modifier = Modifier.fillMaxHeight(),
                                shape = MaterialTheme.shapes.large,
                                padding = AmPadding.ZERO,
                                content = { Row(content = bottomBarItems) }
                            )
                        }
                        AnimatedVisibility(appBarAction.appBarButton?.showButton == true) {
                            AmFilledTonalButton(
                                modifier = Modifier.padding(horizontal = AmPadding.X_SMALL.value),
                                text = appBarAction.appBarButton?.text.orEmpty(),
                                onClick = { appBarAction.appBarButton?.click?.invoke() }
                            )
                        }
                        AnimatedVisibility(appBarAction.appBarButton?.errorMessage != null) {
                            AmCard(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = AmPadding.ZERO.value),
                                shape = MaterialTheme.shapes.large, positive = false
                            ) {
                                AmText(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = AmPadding.MEDIUM.value,
                                            vertical = AmPadding.X_SMALL.value
                                        ),
                                    text = appBarAction.appBarButton?.errorMessage.orEmpty(),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        }
                        AnimatedVisibility(
                            appBarAction.navigateToAccountEditor != null
                                    || appBarAction.navigateToTransactionEditor != null
                        ) {
                            if (appBarAction.navigateToAccountEditor != null) {
                                AmFloatingActionBottom(
                                    amIconsType = AmIcons.AccountAdd,
                                    onClick = { accountEditor(appBarAction.navigateToAccountEditor) }
                                )
                            }
                            if (appBarAction.navigateToTransactionEditor != null) {
                                AmFloatingActionBottom(
                                    amIconsType = AmIcons.TransactionAdd,
                                    onClick = { transactionEditor(appBarAction.navigateToTransactionEditor) }
                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(appBarAction.isLoading) {
                AmCard(padding = AmPadding.ZERO) {
                    AmLinearProgress(
                        modifier = Modifier
                            .height(AmSize.XX_SMALL.value)
                            .width(AmSize.XXX_LARGE.value)
                    )
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
        shape = MaterialTheme.shapes.large,
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
        shape = MaterialTheme.shapes.large, onClick = onClick,
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
        shape = MaterialTheme.shapes.large,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}