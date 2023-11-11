package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.ui.SearchBox
import com.awesome.manager.core.ui.TransactionCard


@Composable
fun TransactionsRoute(
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {

    val transactionState = transactionsViewModel.transactionsState

    TransactionScreen(transactionState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(transactionsState: TransactionsState) {

    val transactions = transactionsState.transactions.collectAsStateWithLifecycle().value
    val searchKey = transactionsState.searchKey.collectAsStateWithLifecycle().value

    Column(Modifier.fillMaxSize()) {
        SearchBox(
            searchKey = searchKey, label = stringResource(R.string.search_for_transaction),
            onSearchKeyChange = transactionsState::onUpdateSearchKey,
            errorMessage = null
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp, top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            content = {
                items(
                    items = transactions,
                    contentType = { "TRANSACTIONS" },
                    key = { transactions -> transactions.id },
                    itemContent = { transaction ->
                        TransactionCard(
                            modifier = Modifier.animateItemPlacement(),
                            account = transaction.accountName,
                            title = transaction.title,
                            subTitle = transaction.subtitle,
                            amount = transaction.amount,
                            pending = transaction.pending,
                            date = transaction.updatedAt,
                            transactionType = transaction.transactionType.title,
                            isPay = transaction.paymentTransaction,
                            currency = transaction.currency.currencyCode,
                            createdBy = transaction.creatorUserId,
                            onClick = {}
                        )
                    }
                )
            }
        )
    }
}