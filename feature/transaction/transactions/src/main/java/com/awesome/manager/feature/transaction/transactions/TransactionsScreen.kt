package com.awesome.manager.feature.transaction.transactions

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION


@Composable
internal fun TransactionsScreen(
    transactionsViewModel: TransactionsViewModel = hiltViewModel(),
) {

    val transactionsState = transactionsViewModel.transactionsState

//    val mainAction = transactionsState.mainAction.collectAsStateWithLifecycle().value
//    LaunchedEffect(key1 = mainAction) {
//        mainAction?.sendMainAction(sendMainAction, transactionsState::doneMainAction)
//    }

    TransactionsScreen(transactionsState)
}

@Composable
internal fun TransactionsScreen(transactionsState: TransactionsState) {

//    val isLoading = transactionsState.refreshing.collectAsStateWithLifecycle().value
    val transactionsLazyPaging = transactionsState.pagingTransactions.collectAsLazyPagingItems()

    val isEmptyList = remember(transactionsLazyPaging.itemCount) {
        transactionsLazyPaging.itemCount == 0
    }

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = isEmptyList,
        label = "TRANSACTIONS",
        contentAlignment = Alignment.TopCenter
    ) {
        when (it) {
            true -> {
                Column(
                    modifier = Modifier
                        .padding(AmPadding.XX_LARGE.value)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AmText(
                        text = stringResource(R.string.theres_no_transactions_yet),
                        maxLines = 3, textAlign = TextAlign.Center
                    )
                    AmFilledTonalButton(
                        text = stringResource(R.string.create_a_transaction),
                        onClick = {/* transactionsState.navigateToCreateTransaction(null) */},
                    )
                }
            }

            false -> {
                AmLazyColumn(
                    isRefreshing = false,
                    onRefresh = transactionsState.refreshTransactions,
                    content = {
                        items(
                            count = transactionsLazyPaging.itemCount,
                            key = transactionsLazyPaging.itemKey { transaction -> transaction.transactionID },
                            contentType = { LAZY_ITEM_TRANSACTION },
                            itemContent = { index ->
                                transactionsLazyPaging[index]?.let { transaction ->
                                    TransactionCard(
                                        modifier = Modifier.animateItem(),
                                        account = transaction.accountName,
                                        title = transaction.title,
                                        amount = transaction.formattedAmount,
                                        isPending = transaction.pending,
                                        date = transaction.transactionAtDate,
                                        transactionType = transaction.transactionType.getString(),
                                        isPay = transaction.transactionType.positive,
                                        currency = transaction.currency.currencySymbol,
                                        onClick = {
//                                            transactionsState.navigateToTransactionDetails(
//                                                accountID = transaction.accountID,
//                                                transactionID = transaction.transactionID
//                                            )
                                        }
                                    )
                                }
                            }
                        )
                    },
                )
            }
        }
    }
}