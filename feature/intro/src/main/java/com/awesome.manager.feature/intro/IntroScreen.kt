package com.awesome.manager.feature.intro

import androidx.compose.runtime.Composable
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun IntroRoute() {
    IntroScreen()
}

@Composable
fun IntroScreen(){
    ScreenPlaceHolder(
        title = "INTRO",
        textButton = "",
        onClickButton = {}
    )
}