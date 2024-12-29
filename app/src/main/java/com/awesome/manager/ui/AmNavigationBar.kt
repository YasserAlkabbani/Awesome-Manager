package com.awesome.manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.core.ui.actions.main.NavigationAction
import com.awesome.manager.navigation.MainDestination
import com.awesome.manager.navigation.isMainDistinction

@Composable
fun NavigationAction.AmBottomNavigation(
    updateNavigation: (NavigationAction) -> Unit
) {
    AmNavigationBar(
        visible = isMainDistinction()
    ) {
        MainDestination.entries.forEach { destination ->
            val navigationDestination = destination.navigationDestination
            AmNavigationItem(
                isSelected = navigationDestination == this,
                title = stringResource(destination.title),
                selectedIcon = destination.selectedAmIconsType,
                unSelectedIcon = destination.unSelectedAmIconsType,
                onSelect = { updateNavigation(navigationDestination) }
            )
        }
    }

}