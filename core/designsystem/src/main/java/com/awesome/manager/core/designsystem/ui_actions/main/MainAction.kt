package com.awesome.manager.core.designsystem.ui_actions.main

import com.awesome.manager.core.designsystem.ui_actions.appbar.AppBarAction
import com.awesome.manager.core.designsystem.ui_actions.bottomsheet.BottomSheetAction
import com.awesome.manager.core.designsystem.ui_actions.navigation.NavigationAction
import com.awesome.manager.core.designsystem.ui_actions.picker.PickerAction

sealed class MainAction {
    data class Navigate(val navigationAction: NavigationAction) : MainAction()
    data class BottomSheet(val bottomSheetAction: BottomSheetAction) : MainAction()
    data class AppBar(val appBarAction: AppBarAction) : MainAction()
    data class Pick(val pickAction: PickerAction) : MainAction()
}





