package com.awesome.manager.feature.auth

import androidx.compose.runtime.Composable
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun AuthRoute(){
    AuthScreen()
}


@Composable
fun AuthScreen(){
    ScreenPlaceHolder(
        title = "AUTH",
        textButton = "",
        onClickButton = {}
    )
}