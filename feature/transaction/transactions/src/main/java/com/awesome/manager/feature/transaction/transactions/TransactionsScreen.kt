package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmButton


@Composable
fun TransactionsRoute(
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
){

    val transactionState=transactionsViewModel.transactionState

    TransactionScreen(transactionState)
}


@Composable
fun TransactionScreen(transactionState: TransactionState) {

    val transactions=transactionState.transactions.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp) ,
        content = {
            items(
                items = transactions,
                contentType = {"TRANSACTIONS"},
                key = {transactions->transactions.id},
                itemContent = {transaction->
                    AmButton(
                        text = transaction.title,
                        onClick = {}
                    )
                }
            )
        }
    )
}