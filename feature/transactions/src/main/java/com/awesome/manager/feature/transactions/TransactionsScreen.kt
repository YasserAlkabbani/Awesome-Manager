package com.awesome.manager.feature.transactions

import androidx.compose.runtime.Composable
import com.awesome.manager.core.ui.ScreenPlaceHolder

@Composable
fun TransactionsRoute(){
    TransactionScreen()
}


@Composable
fun TransactionScreen(){
    ScreenPlaceHolder(
        title = "TRANSACTIONS",
        textButton = "",
        onClickButton = {}
    )
}