package com.awesome.manager.feature.accounts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.awesome.manager.core.ui.ScreenPlaceHolder


@Composable
fun AccountsRoute(){
    AccountsScreen()
}


@Composable
fun AccountsScreen(){
    ScreenPlaceHolder(
        title = "ACCOUNTS",
        textButton = "",
        onClickButton = {}
    )
}