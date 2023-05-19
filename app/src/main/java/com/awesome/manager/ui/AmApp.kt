package com.awesome.manager.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.component.MaAppBar
import com.awesome.manager.core.designsystem.component.MaNavigationBar
import com.awesome.manager.core.designsystem.component.MaNavigationItem
import com.awesome.manager.core.designsystem.icon.MaIcons
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp(
    maAppState: AmAppState= rememberAmAppState()
) {
    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize()) {
       Scaffold(
           topBar = {
               if (maAppState.shouldShowShowTopBar)
               MaAppBar(
                   modifier = Modifier,
                   title = "TEST_TOP_APPBAR_TITLE",
                   navigationIcon = MaIcons.ArrowBack,
                   actionIcon = MaIcons.ArrowBack,
                   onNavigationClick = {  },
                   onActionClick = {},
               )
           } ,
           bottomBar = {
               if (maAppState.shouldShowBottomBar)
               MaBottomBar(
                   modifier = Modifier,
                   mainDestinations = maAppState.mainDestination,
                   onNavigationToDestination = maAppState::navigateToMainDestination,
                   selectedMainDestination = maAppState.currentMainDestination
               )
           }
       ) {padding->
           AmNavHost(
               modifier = Modifier.padding(padding),
               amAppState = maAppState
           )
       }
    }
}


@Composable
fun MaBottomBar(
    modifier: Modifier,
    mainDestinations: List<MainDestination>,
    onNavigationToDestination:(MainDestination)->Unit,
    selectedMainDestination: MainDestination?
){
    MaNavigationBar(
        modifier=modifier,
        content = {
            mainDestinations.forEach {destination->
                MaNavigationItem(
                    modifier = Modifier,
                    isSelected = destination==selectedMainDestination,
                    selectedIcon = destination.selectedMaIconsType,
                    unSelectedIcon = destination.unSelectedMaIconsType,
                    label = stringResource(id = destination.titleTextId),
                    onSelect = {onNavigationToDestination(destination)}
                )
            }
        }
    )
}