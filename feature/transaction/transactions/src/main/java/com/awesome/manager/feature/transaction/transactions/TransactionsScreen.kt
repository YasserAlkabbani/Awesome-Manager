package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_BOTTOM
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_TOP
import com.awesome.manager.core.designsystem.UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.AmSearch
import com.awesome.manager.core.ui.TransactionCard


@Composable
fun TransactionsRoute(
    navigateToTransactionDetails: (AmTransaction) -> Unit,
    updateAppBarState: (AppBarData?) -> Unit,
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {

    val transactionsState = transactionsViewModel.transactionsState


    LaunchedEffect(key1 = Unit, block = {
        updateAppBarState(null)
    })

    val transactionDetailsNavigation =
        transactionsState.transactionNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = transactionDetailsNavigation, block = {
        transactionDetailsNavigation?.let {
            transactionsState.doneTransactionNavigation()
            navigateToTransactionDetails(it)
        }
    })

    TransactionScreen(transactionsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(transactionsState: TransactionsState) {

    val transactions = transactionsState.transactions.collectAsStateWithLifecycle().value
    val searchKey = transactionsState.searchKey.collectAsStateWithLifecycle().value

    Column(Modifier.fillMaxSize()) {
        AmSearch(
            searchKey = searchKey, searchLabel = stringResource(R.string.search_for_transaction),
            onSearchKeyChange = transactionsState::onUpdateSearchKey,
            errorMessage = null,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = SCROLL_CONTENT_PADDING_TOP.dp,
                bottom = SCROLL_CONTENT_PADDING_BOTTOM.dp
            ),
            verticalArrangement = Arrangement.spacedBy(VERTICAL_SPACE_BETWEEN_ITEMS.dp),
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
                            onClick = {
                                transactionsState.navigationToTransactionNavigation(
                                    transaction
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}