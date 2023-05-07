package com.awesome.manager.feature.home

import androidx.compose.runtime.Composable
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun HomeRoute(){
    HomeScreen()
}


@Composable
fun HomeScreen(){
    ScreenPlaceHolder(
        title = "HOME",
        textButton = "",
        onClickButton = {}
    )
}