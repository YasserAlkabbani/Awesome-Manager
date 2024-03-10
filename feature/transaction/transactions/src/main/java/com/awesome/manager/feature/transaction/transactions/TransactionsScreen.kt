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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_BOTTOM
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_TOP
import com.awesome.manager.core.designsystem.UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS
import com.awesome.manager.core.ui.TransactionCard


@Composable
fun TransactionsRoute(
    sendMainAction :(MainActions)->Unit,
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {

    val transactionsState = transactionsViewModel.transactionsState

    val navigationAction =
        transactionsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendAction(
            sendMainAction=sendMainAction,
            resetNavigation = transactionsState::resetNavigationAction
        )
    })

    TransactionScreen(transactionsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(transactionsState: TransactionsState) {

    val transactionsListState = transactionsState.transactions.collectAsState().value

    Column(Modifier.fillMaxSize()) {
        when(transactionsListState){
            is DataState.Success -> {
                val transactions=transactionsListState.data
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
                                        transactionsState.navigateToTransaction(
                                            transaction.id
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            }
            DataState.Error , DataState.Loading -> {}
        }

    }
}