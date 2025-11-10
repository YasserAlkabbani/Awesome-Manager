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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.text.asString
import com.awesome.manager.core.model.AmTransactionWithDetails
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION
import kotlinx.coroutines.flow.Flow


@Composable
internal fun TransactionsRoute(
    navigateToCreateTransaction: () -> Unit,
    navigateToTransactionDetails: (accountID: String, transactionID: String) -> Unit,
    transactionsViewModel: TransactionsViewModel = hiltViewModel(),
) {

    val transactionsState =
        transactionsViewModel.transactionsState.collectAsStateWithLifecycle().value

    val transactionsEvent =
        transactionsViewModel.transactionsEvent.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = transactionsEvent) {
        when (transactionsEvent) {
            is TransactionsEvents.Idle -> Unit
            is TransactionsEvents.NavigationCreateTransaction -> navigateToCreateTransaction()
            is TransactionsEvents.NavigationTransactionDetails -> navigateToTransactionDetails(
                transactionsEvent.accountID,
                transactionsEvent.transactionID
            )
        }
        if (transactionsEvent !is TransactionsEvents.Idle) transactionsViewModel.doneTransactionEvent()
    }


//    val mainAction = transactionsState.mainAction.collectAsStateWithLifecycle().value
//    LaunchedEffect(key1 = mainAction) {
//        mainAction?.sendMainAction(sendMainAction, transactionsState::doneMainAction)
//    }

    TransactionsScreen(
        transactionsState = transactionsState,
        transactionsPaging = transactionsViewModel.pagingTransactions,
        refreshTransactions = transactionsViewModel::refreshTransactions,
        navigateToCreateTransaction = transactionsViewModel::navigateToCreateTransaction,
        navigateToTransactionDetails = transactionsViewModel::navigateToTransactionDetails,
    )
}

@Composable
internal fun TransactionsScreen(
    transactionsState: TransactionsState,
    transactionsPaging: Flow<PagingData<AmTransactionWithDetails>>,
    refreshTransactions: () -> Unit,
    navigateToCreateTransaction: () -> Unit,
    navigateToTransactionDetails: (accountID: String, transactionID: String) -> Unit,
) {

    val transactionsLazyPaging = transactionsPaging.collectAsLazyPagingItems()

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
                        onClick = navigateToCreateTransaction,
                    )
                }
            }

            false -> {
                AmLazyColumn(
                    isRefreshing = false,
                    onRefresh = refreshTransactions,
                    content = {
                        items(
                            count = transactionsLazyPaging.itemCount,
                            key = transactionsLazyPaging.itemKey { transaction -> transaction.transaction.transactionID },
                            contentType = { LAZY_ITEM_TRANSACTION },
                            itemContent = { index ->
                                transactionsLazyPaging[index]?.let { transactionWithDetails ->
                                    TransactionCard(
                                        modifier = Modifier.animateItem(),
                                        account = transactionWithDetails.accountName,
                                        title = transactionWithDetails.transaction.title,
                                        amount = transactionWithDetails.transaction.formattedAmount,
                                        isPending = transactionWithDetails.transaction.pending,
                                        date = transactionWithDetails.transaction.transactionAtDate,
                                        transactionType = transactionWithDetails.transactionType.asString(),
                                        isPay = transactionWithDetails.isPositive,
                                        currency = transactionWithDetails.currencyCode,
                                        onClick = {
                                            navigateToTransactionDetails(
                                                transactionWithDetails.transaction.accountID,
                                                transactionWithDetails.transaction.transactionID
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
    }
}