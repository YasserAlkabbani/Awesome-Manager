package com.awesome.manager.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.icon.AmIconsType

@Composable
fun AmCustomBottomBarWithFab(
    modifier: Modifier,
    onClickFab: () -> Unit,
    fabIcon: AmIconsType,
    bottomBarItems: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AmCustomNavigationBar(
                modifier = Modifier.wrapContentWidth(),
                content = bottomBarItems,
            )
            AmSpacerMediumWidth()
            FloatingActionButton(
                modifier = Modifier,
                onClick = onClickFab,
                content = { AmIcon(amIconsType = fabIcon) },
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                shape = MaterialTheme.shapes.extraLarge,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }

}

@Composable
fun AmCustomNavigationBar(
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Row(content = content)
    }
}

@Composable
fun RowScope.AmNavigationCustomItem(
    modifier: Modifier,
    isSelected: Boolean,
    selectedIcon: AmIconsType,
    unSelectedIcon: AmIconsType,
    onSelect: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(4.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors().copy(
            contentColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
        ),
        onClick = onSelect
    ) {
        AmIcon(
            modifier = modifier
                .padding(8.dp)
                .size(24.dp),
            amIconsType = if (isSelected) selectedIcon else unSelectedIcon,
        )
    }
}