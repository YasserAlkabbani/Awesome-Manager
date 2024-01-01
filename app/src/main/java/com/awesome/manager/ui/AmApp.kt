package com.awesome.manager.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.core.designsystem.component.AmAppBar
import com.awesome.manager.core.designsystem.component.AmExtendedFloatingActionButton
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AmApp(
    maAppState: AmAppState = rememberAmAppState()
) {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainActivityState = mainActivityViewModel.mainActivityState

    val loginState = mainActivityState.isLogin.collectAsStateWithLifecycle().value
    val appBarState = mainActivityState.appBarState.collectAsStateWithLifecycle().value

    val currentDestination = maAppState.currentDestination

    val currentMainDestination = maAppState.currentMainDestination


    LaunchedEffect(key1 = loginState, key2 = currentDestination, block = {
        currentDestination?.route?.let {
            maAppState.navigateByAuthState(loginState, it)
        }
    })

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                appBarState?.let { appBarData ->
                    AmAppBar(
                        modifier = Modifier,
                        appBarData = appBarData
                    )
                }
            },
            bottomBar = {
                if (maAppState.shouldShowBottomBar) {
                    MaBottomBar(
                        modifier = Modifier,
                        mainDestinations = maAppState.mainDestination,
                        onNavigationToDestination = maAppState::navigateToMainDestination,
                        selectedMainDestination = maAppState.currentMainDestination
                    )
                }
            },
            floatingActionButton = {
                maAppState.currentMainDestination?.addButton?.let { addButton ->
                    AmExtendedFloatingActionButton(
                        modifier = Modifier,
                        expanded = true,
                        text = addButton.title.asText(),
                        icon = addButton.icon,
                        onClick = {
                            currentDestination?.route?.let {
                                maAppState.navigateToAddByCurrentNavigation(
                                    it
                                )
                            }
                        }
                    )
                }
            },
        ) { padding ->
            AmNavHost(
                modifier = Modifier.padding(padding),
                amAppState = maAppState,
                updateAppBarState = mainActivityState::updateAppBarState
            )
        }
    }
}


@Composable
fun MaBottomBar(
    modifier: Modifier,
    mainDestinations: List<MainDestination>,
    onNavigationToDestination: (MainDestination) -> Unit,
    selectedMainDestination: MainDestination?
) {
    AmNavigationBar(
        modifier = modifier,
        content = {
            mainDestinations.forEach { destination ->
                AmNavigationItem(
                    modifier = Modifier,
                    isSelected = destination == selectedMainDestination,
                    selectedIcon = destination.selectedAmIconsType,
                    unSelectedIcon = destination.unSelectedAmIconsType,
                    label = destination.title.asText(),
                    onSelect = { onNavigationToDestination(destination) }
                )
            }
        }
    )
}