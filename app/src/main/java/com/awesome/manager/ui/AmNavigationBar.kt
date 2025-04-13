package com.awesome.manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.navigation.MainDestination

@Composable
fun AmBottomNavigation(
    currentMainDestination: MainDestination?,
    navigateTo: (MainDestination) -> Unit,
) {
    AmNavigationBar(
        visible = currentMainDestination != null
    ) {
        MainDestination.entries.forEach { mainDestination ->
            AmNavigationItem(
                isSelected = mainDestination == currentMainDestination,
                title = stringResource(mainDestination.title),
                selectedIcon = mainDestination.selectedAmIconsType,
                unSelectedIcon = mainDestination.unSelectedAmIconsType,
                onSelect = { navigateTo(mainDestination) }
            )
        }
    }

}