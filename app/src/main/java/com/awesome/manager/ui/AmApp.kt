package com.awesome.manager.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp(
    maAppState: AmAppState= rememberAmAppState()
) {
    val mainActivityViewModel:MainActivityViewModel= viewModel()

    val loginState=mainActivityViewModel.mainActivityState.isLogin.collectAsStateWithLifecycle().value
    val currentDestinationRoute=maAppState.currentDestination?.route

    LaunchedEffect(key1 = loginState,key2=currentDestinationRoute, block = {
        currentDestinationRoute?.let {
            maAppState.navigateByAuthState(loginState,currentDestinationRoute)
        }
    })

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Surface(modifier = Modifier.fillMaxSize()) {
       Scaffold(
           topBar = {
               if (maAppState.shouldShowShowTopBar){
                   AmAppBar(
                       modifier = Modifier,
                       title = "TEST_TOP_APPBAR_TITLE".asAmText(),
                       navigationIcon = AmIcons.ArrowBack,
                       actionIcon = AmIcons.ArrowBack,
                       onNavigationClick = {  },
                       onActionClick = {},
                   )
               }
           } ,
           bottomBar = {
               if (maAppState.shouldShowBottomBar){
                   MaBottomBar(
                       modifier = Modifier,
                       mainDestinations = maAppState.mainDestination,
                       onNavigationToDestination = maAppState::navigateToMainDestination,
                       selectedMainDestination = maAppState.currentMainDestination
                   )
               }
           },
           floatingActionButton = {

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
    AmNavigationBar(
        modifier=modifier,
        content = {
            mainDestinations.forEach {destination->
                AmNavigationItem(
                    modifier = Modifier,
                    isSelected = destination==selectedMainDestination,
                    selectedIcon = destination.selectedAmIconsType,
                    unSelectedIcon = destination.unSelectedAmIconsType,
                    label = destination.title,
                    onSelect = {onNavigationToDestination(destination)}
                )
            }
        }
    )
}