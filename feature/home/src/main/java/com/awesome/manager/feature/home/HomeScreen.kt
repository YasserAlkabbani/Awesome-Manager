package com.awesome.manager.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun HomeRoute(showProfile: () -> Unit) {
    HomeScreen()
}


@Composable
fun HomeScreen() {
    ScreenPlaceHolder(
        modifier = Modifier.fillMaxSize(),
        title = "HOME",
        textButton = "",
        onClickButton = {}
    )
}