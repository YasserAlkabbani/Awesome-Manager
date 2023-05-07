package com.awesome.manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.awesome.manager.navigation.MainDistination
import kotlinx.coroutines.CoroutineScope

@Stable
class AmAppState(
    val navHostController: NavHostController,
    val coroutineScope: CoroutineScope,
) {

    val currentDistanation: String?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination?.route


    val currentMainDistination
        @Composable get() = when (currentDistanation) {
            else -> null
        }


    val shouldShowBottomBar
        @Composable get() = currentDistanation != null

    val showShowTopBar
        @Composable get() = currentDistanation == null

    val mainDistination=MainDistination.values().toList()

    @Composable
    fun navigateToMainDestination(mainDistination: MainDistination){

    }

}