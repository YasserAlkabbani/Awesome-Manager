package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.ui.TransactionCard


@Composable
fun TransactionsRoute(
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
){

    val transactionState=transactionsViewModel.transactionState

    TransactionScreen(transactionState)
}


@OptIn(ExperimentalFoundationApi::class)
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
                    TransactionCard(
                        modifier = Modifier.animateItemPlacement(),
                        title = transaction.title,
                        subTitle = transaction.subtitle,
                        amount = transaction.amount,
                        date = transaction.updatedAt,
                        transactionType = transaction.transactionType.title,
                        isPay = transaction.isPay,
                        currency = transaction.account.currency.currencySymbol,
                        createdBy = transaction.creatorUserId,
                        onClick = {}
                    )
                }
            )
        }
    )
}