package com.awesome.manager.core.designsystem.ui_actions

sealed class MainActions {
    data class Navigate(val navigationAction: NavigationAction) : MainActions()
    data class BottomSheet(val bottomSheetAction: BottomSheetAction) : MainActions()
    data class AppBar(val appBarAction: AppBarAction) : MainActions()
}





