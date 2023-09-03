package com.awesome.manager.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.awesome.manager.MainActivityViewModel
import com.awesome.manager.R
import com.awesome.manager.core.designsystem.component.AmAppBar
import com.awesome.manager.core.designsystem.component.AmExtendedFloatingActionButton
import com.awesome.manager.core.designsystem.component.AmNavigationBar
import com.awesome.manager.core.designsystem.component.AmNavigationItem
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.navigation.AmNavHost
import com.awesome.manager.navigation.MainDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmApp(
    maAppState: AmAppState= rememberAmAppState()
) {
    val mainActivityViewModel:MainActivityViewModel= viewModel()
    val mainActivityState=mainActivityViewModel.mainActivityState
    val scaffoldActions=mainActivityViewModel.mainActivityState.scaffoldActions

    val loginState=mainActivityViewModel.mainActivityState.isLogin.collectAsStateWithLifecycle().value

    val currentDestination=maAppState.currentDestination

    val currentMainDestination= maAppState.currentMainDestination

    LaunchedEffect(key1 = loginState,key2=currentDestination, block = {
        currentDestination?.route?.let {
            maAppState.navigateByAuthState(loginState,it)
        }
    })

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Surface(modifier = Modifier.fillMaxSize()) {
       Scaffold(
           topBar = {
               if(maAppState.shouldShowShowTopBar) {
                   AmAppBar(
                       modifier = Modifier,
                       title = currentDestination?.label.toString(),
                       onBack = mainActivityState::onNavigationBack,
                       onSave = scaffoldActions::onSave, showSave = maAppState.shouldShowShowTopBarSave,
                       onEdit = scaffoldActions::onEdit , showEdit = maAppState.shouldShowShowTopBarEdit,
                   )
               }
           } ,
           bottomBar = {
               if(maAppState.shouldShowBottomBar){
                   MaBottomBar(
                       modifier = Modifier,
                       mainDestinations = maAppState.mainDestination,
                       onNavigationToDestination = maAppState::navigateToMainDestination,
                       selectedMainDestination = maAppState.currentMainDestination
                   )
               }
           },
           floatingActionButton = {
               if(maAppState.shouldShowFloatingActionButton) {
                   maAppState.currentMainDestination?.let {
                       AmExtendedFloatingActionButton(
                           modifier = Modifier,
                           expanded = true,
                           text = it.title.asText(),
                           icon = it.addIcon,
                           onClick = {currentDestination?.route?.let { maAppState.navigateToAddByCurrentNavigation(it) }}
                       )
                   }
               }
           },
       ) {padding->
           AmNavHost(
               modifier = Modifier.padding(padding),
               amAppState = maAppState,
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
                    label = destination.title.asText(),
                    onSelect = {onNavigationToDestination(destination)}
                )
            }
        }
    )
}