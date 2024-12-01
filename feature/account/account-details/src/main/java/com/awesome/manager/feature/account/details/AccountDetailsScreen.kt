package com.awesome.manager.feature.account.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION

@Composable
fun AccountDetailsRoute(
    sendMainAction: (MainAction) -> Unit,
    accountDetailsViewModel: AccountDetailsViewModel = hiltViewModel(),
) {

    val accountDetailsState = accountDetailsViewModel.accountDetailsState

    accountDetailsState.accountDetailsUI.collectAsStateWithLifecycle(null)

    val mainAction = accountDetailsState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, accountDetailsState::doneMainAction)
    }



    AccountDetailsScreen(accountDetailsState)
}

@Composable
fun AccountDetailsScreen(accountDetailsState: AccountDetailsState) {

    val accountState: AmUIState<AmAccount> =
        accountDetailsState.account.collectAsStateWithLifecycle().value
    val refreshingTransactions: Boolean =
        accountDetailsState.refreshing.collectAsStateWithLifecycle().value
    val transactionsLazyPaging: LazyPagingItems<AmTransaction> =
        accountDetailsState.transactions.collectAsLazyPagingItems()

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
        targetState = accountState,
        label = "ACCOUNT_DETAILS"
    ) {
        when (it) {
            is AmUIState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AmPadding.MEDIUM.value),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AmPadding.SMALL.value),
                ) {
                    val account = it.data
                    val balanceDetails = account.balanceDetails
                    AccountCard(
                        modifier = Modifier,
                        title = account.name,
                        imageUrl = account.imageUrl, loading = account.pending,
                        withDetails = true, onClick = null,
                        onAddTransaction = null, onEditTransaction = null,
                        income = balanceDetails.formattedIncome,
                        expenses = balanceDetails.formattedExpenses,
                        netIncomeAbs = balanceDetails.formattedNetIncome,
                        debtor = balanceDetails.formattedDebtor,
                        creditor = balanceDetails.formattedCreditor,
                        netDebtorAbs = balanceDetails.formattedNetDebtor,
                        currencySymbol = balanceDetails.currency.currencySymbol,
                        isPositiveIncome = balanceDetails.isPositiveIncome,
                        isPositiveDebtor = balanceDetails.isPositiveDebtor,
                    )
                    AmLazyColumn(
                        isRefreshing = refreshingTransactions,
                        onRefresh = accountDetailsState.refreshTransactions,
                        content = {
                            items(
                                count = transactionsLazyPaging.itemCount,
                                contentType = { LAZY_ITEM_TRANSACTION },
                                key = transactionsLazyPaging.itemKey { transaction -> transaction.id },
                                itemContent = { index ->
                                    transactionsLazyPaging[index]?.let { transaction ->
                                        TransactionCard(
                                            modifier = Modifier.animateItem(),
                                            account = transaction.accountName,
                                            title = transaction.title,
                                            amount = transaction.formattedAmount,
                                            pending = transaction.pending,
                                            date = transaction.transactionAtDate,
                                            transactionType = transaction.transactionType.getString(),
                                            isPay = transaction.transactionType.positive,
                                            currency = transaction.currency.currencySymbol,
                                            onClick = {
                                                accountDetailsState.navigateToTransactionDetails(
                                                    transaction.id
                                                )
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            }

            is AmUIState.Error -> Unit
            is AmUIState.Loading -> Unit
        }
    }


}