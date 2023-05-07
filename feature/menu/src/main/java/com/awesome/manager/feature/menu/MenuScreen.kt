package com.awesome.manager.feature.menu

import androidx.compose.runtime.Composable
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun MenuRoute(){
    MenuScreen()
}


@Composable
fun MenuScreen(){
    ScreenPlaceHolder(
        title = "MENU",
        textButton = "",
        onClickButton = {}
    )
}