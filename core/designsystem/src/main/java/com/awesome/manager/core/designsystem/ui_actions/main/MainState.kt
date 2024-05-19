package com.awesome.manager.core.designsystem.ui_actions.main

import com.awesome.manager.core.designsystem.ui_actions.appbar.MainAppBar
import com.awesome.manager.core.designsystem.ui_actions.appbar.MainAppBarState
import com.awesome.manager.core.designsystem.ui_actions.bottomsheet.MainBottomSheet
import com.awesome.manager.core.designsystem.ui_actions.bottomsheet.MainBottomSheetState
import com.awesome.manager.core.designsystem.ui_actions.navigation.MainNavigationState
import com.awesome.manager.core.designsystem.ui_actions.navigation.MainNavigation
import com.awesome.manager.core.designsystem.ui_actions.picker.MainPicker
import com.awesome.manager.core.designsystem.ui_actions.picker.MainPickerState

abstract class MainState :
    MainNavigation by MainNavigationState(), MainAppBar by MainAppBarState(),
    MainBottomSheet by MainBottomSheetState(), MainPicker by MainPickerState()
