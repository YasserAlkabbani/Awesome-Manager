package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_BOTTOM
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_TOP
import com.awesome.manager.core.designsystem.UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.ui_actions.AppBarAction
import com.awesome.manager.core.ui.TransactionCard
import timber.log.Timber


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
    Timber.d("TEST_APP_BAR_STATE TRANSACTIONS RE_COMPOSE")
    val transactions=transactionsState.transactions.collectAsState().value
    when (transactions){
        is DataState.Success ,DataState.Loading,DataState.Error-> AppBarAction.MainNavigation(
            onAddAccount = null,
            onAddTransaction = {transactionsState.navigateToCreateTransaction(null)}
        ).sendAction(sendMainAction)
    }

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
            DataState.Error -> {
                Column(
                    modifier = Modifier
                        .padding(UIConstant.PADDING_LARGE_EXTRA.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AmText(
                        text = stringResource(R.string.theres_no_transactions_yet),
                        maxLines = 3, textAlign = TextAlign.Center
                    )
                    AmFilledTonalButton(
                        text = stringResource(R.string.create_a_transaction),
                        onClick = {transactionsState.navigateToCreateTransaction(null)},
                        positive = null
                    )
                }
            }
            DataState.Loading -> {}
        }

    }
}