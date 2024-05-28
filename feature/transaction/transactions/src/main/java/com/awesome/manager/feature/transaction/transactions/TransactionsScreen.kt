package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.ui_actions.navigation.sendMainAction
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION


@Composable
fun TransactionsRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {

    val transactionsState = transactionsViewModel.transactionsState

    val navigationAction = transactionsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, transactionsState::resetNavigationAction)
    })

    val appBarAction = transactionsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, transactionsState::resetAppBar)
    })

    val bottomSheetAction = transactionsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, transactionsState::idleBottomSheet)
    })

    TransactionScreen(transactionsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(transactionsState: TransactionsState) {

    val transactionsLazyPaging = transactionsState.transactions.collectAsLazyPagingItems()
    val loadState = transactionsLazyPaging.loadState.refresh

    Column(Modifier.fillMaxSize()) {
        AmLazyColumn(
            content = {
                if (transactionsLazyPaging.itemCount == 0)
                    item {
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
                                onClick = { transactionsState.navigateToCreateTransaction(null) },
                                positive = null
                            )
                        }
                    }
                else items(
                    count = transactionsLazyPaging.itemCount,
                    key = transactionsLazyPaging.itemKey { transaction -> transaction.id },
                    contentType = { LAZY_ITEM_TRANSACTION },
                    itemContent = { index ->
                        transactionsLazyPaging[index]?.let { transaction ->
                            TransactionCard(
                                modifier = Modifier.animateItemPlacement(),
                                account = transaction.accountName,
                                title = transaction.title,
                                subTitle = transaction.subtitle,
                                amount = transaction.amount,
                                pending = transaction.pending,
                                date = transaction.updatedAt,
                                transactionType = transaction.transactionType.name,
                                isPay = transaction.transactionType.posative,
                                currency = transaction.currency.currencyCode,
                                createdBy = transaction.creatorUserId,
                                onClick = {
                                    transactionsState.navigateToTransactionDetails(
                                        transaction.id
                                    )
                                }
                            )
                        }
                    }
                )
            },
        )
    }
}