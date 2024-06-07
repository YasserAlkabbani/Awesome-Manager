package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.component.text.AmText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmDynamicBottomBar(
    modifier: Modifier,
    bottomBarItems: @Composable RowScope.() -> Unit,
    appBarAction: AppBarAction
) {
    AnimatedVisibility(appBarAction.visible) {
        Surface(
            modifier = modifier.animateContentSize(),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.05f)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(visible = appBarAction.onAddAccount != null) {
                    Row {
                        AmFloatingActionBottom(
                            amIconsType = AmIcons.AccountAdd,
                            onClick = { appBarAction.onAddAccount?.invoke() })
                    }
                }
                AnimatedVisibility(visible = appBarAction.onClickBackButton != null) {
                    AmActionCustomItem(
                        amIconsType = AmIcons.ArrowBack,
                        onClick = { appBarAction.onClickBackButton?.invoke() }
                    )
                }
                AnimatedVisibility(visible = appBarAction.onClickCancelButton != null) {
                    AmActionCustomItem(
                        amIconsType = AmIcons.Close,
                        onClick = { appBarAction.onClickCancelButton?.invoke() }
                    )
                }
                AnimatedVisibility(visible = appBarAction.bottomNavigation) {
                    Row {
                        Surface(
                            shape = MaterialTheme.shapes.extraLarge,
                            content = { Row(content = bottomBarItems) }
                        )
                    }
                }
                AnimatedVisibility(visible = appBarAction.buttonOnClick != null) {
                    AmButton(
                        text = appBarAction.buttonText,
                        positive = null,
                        onClick = { appBarAction.buttonOnClick?.invoke() }
                    )
                }
                AnimatedVisibility(visible = appBarAction.errorMessage != null) {
                    AmCard(positive = false) {
                        AmText(text = appBarAction.errorMessage.orEmpty())
                    }
                }
                AnimatedVisibility(visible = appBarAction.onAddTransaction != null) {
                    Row {
                        AmFloatingActionBottom(
                            amIconsType = AmIcons.TransactionAdd,
                            onClick = { appBarAction.onAddTransaction?.invoke() })
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
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors().copy(
            contentColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
        ),
        onClick = onSelect
    ) {
        AmIcon(
            modifier = modifier
                .padding(AmPadding.MEDIUM.value)
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
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors().copy(
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onClick
    ) {
        AmIcon(
            modifier = modifier
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