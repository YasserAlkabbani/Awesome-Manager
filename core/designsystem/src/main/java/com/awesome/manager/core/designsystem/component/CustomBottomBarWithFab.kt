package com.awesome.manager.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.awesome.manager.core.designsystem.component.buttons.AmButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.designsystem.ui_actions.appbar.AppBarAction

@Composable
fun AmCustomBottomBarWithFab(
    modifier: Modifier,
    bottomBarItems: @Composable RowScope.() -> Unit,
    appBarAction: AppBarAction
) {
    if (appBarAction !is AppBarAction.Idle) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.05f)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (appBarAction) {
                    AppBarAction.Idle -> {}
                    is AppBarAction.MainNavigation -> {
                        Surface(
                            shape = MaterialTheme.shapes.extraLarge,
                            content = { Row(content = bottomBarItems) }
                        )
                        AnimatedVisibility(visible = appBarAction.onAddAccount != null) {
                            Row {
                                AmSpacerMediumWidth()
                                AmFloatingActionBottom(
                                    amIconsType = AmIcons.AccountAdd,
                                    onClick = { appBarAction.onAddAccount?.invoke() })
                            }
                        }
                        AnimatedVisibility(visible = appBarAction.onAddTransaction != null) {
                            Row {
                                if (appBarAction.onAddAccount == null) AmSpacerMediumWidth()
                                AmFloatingActionBottom(
                                    amIconsType = AmIcons.TransactionAdd,
                                    onClick = { appBarAction.onAddTransaction?.invoke() })
                            }
                        }
                    }

                    is AppBarAction.Read -> {
                        AmActionCustomItem(
                            amIconsType = AmIcons.ArrowBack,
                            onClick = appBarAction.onBack
                        )
                        if (appBarAction.canEdit) {
                            AmSpacerMediumWidth()
                            AmButton(
                                text = appBarAction.title,
                                positive = null,
                                onClick = appBarAction.onEdit
                            )
                        }
                        AnimatedVisibility(visible = appBarAction.onAddTransaction != null) {
                            AmSpacerMediumWidth()
                            AmFloatingActionBottom(
                                amIconsType = AmIcons.TransactionAdd,
                                onClick = { appBarAction.onAddTransaction?.invoke() })
                        }
                    }

                    is AppBarAction.Create -> {
                        AmActionCustomItem(
                            amIconsType = AmIcons.ArrowBack,
                            onClick = appBarAction.onCancel
                        )
                        AmSpacerMediumWidth()
                        AmButton(
                            text = appBarAction.title,
                            positive = null,
                            onClick = appBarAction.onCreate
                        )
                    }

                    is AppBarAction.Edit -> {
                        AmActionCustomItem(
                            amIconsType = AmIcons.Close,
                            onClick = appBarAction.onCancel
                        )
                        AmSpacerMediumWidth()
                        AmButton(
                            text = appBarAction.title,
                            positive = null,
                            onClick = appBarAction.onUpdate
                        )
                    }

                    is AppBarAction.Search -> {

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
        modifier = modifier.padding(AmPadding.EXTRA_SMALL.value),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors().copy(
            contentColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
        ),
        onClick = onSelect
    ) {
        AmIcon(
            modifier = modifier
                .padding(AmPadding.EXTRA_LARGE.value)
                .size(24.dp),
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
        modifier = modifier.padding(1.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors().copy(
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onClick
    ) {
        AmIcon(
            modifier = modifier
                .padding(8.dp)
                .size(24.dp),
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