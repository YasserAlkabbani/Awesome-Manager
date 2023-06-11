package com.awesome.manager.feature.transaction.search


@Composable
fun TransactionsRoute(onSelectTransaction:(AmTransaction)->Unit){
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