package com.awesome.manager.feature.intro

import androidx.compose.runtime.Composable
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun IntroRoute(sendMainAction: (MainActions) -> Unit) {
    IntroScreen()
}

@Composable
fun IntroScreen() {
    ScreenPlaceHolder(
        title = "INTRO",
        textButton = "",
        onClickButton = {}
    )
}