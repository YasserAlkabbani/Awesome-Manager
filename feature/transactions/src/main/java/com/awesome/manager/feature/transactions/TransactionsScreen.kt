package com.awesome.manager.feature.transactions

import androidx.compose.runtime.Composable
import com.awesome.manager.core.ui.ScreenPlaceHolder
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TransactionsRoute(
    clickFab: StateFlow<Unit?>,
    doneClickFab:()->Unit,
){
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