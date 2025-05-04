package com.awesome.manager.feature.account.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.model.AmAccountWithBalance
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.card.CardBalanceDetails
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION

@Composable
internal fun AccountDetailsScreen(
    accountDetailsViewModel: AccountDetailsViewModel = hiltViewModel(),
) {

    val accountDetailsState = accountDetailsViewModel.accountDetailsState

    accountDetailsState.accountDetailsUI.collectAsStateWithLifecycle(null)

//    val mainAction = accountDetailsState.mainAction.collectAsState().value
//    LaunchedEffect(key1 = mainAction) {
//        mainAction?.sendMainAction(sendMainAction, accountDetailsState::doneMainAction)
//    }



    AccountDetailsScreen(accountDetailsState)
}

@Composable
internal fun AccountDetailsScreen(accountDetailsState: AccountDetailsState) {

    val accountState: AmState<AmAccountWithBalance> =
        accountDetailsState.account.collectAsStateWithLifecycle().value
//    val refreshingTransactions: Boolean =
//        accountDetailsState.refreshing.collectAsStateWithLifecycle().value
    val transactionsLazyPaging: LazyPagingItems<AmTransaction> =
        accountDetailsState.transactions.collectAsLazyPagingItems()

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
        targetState = accountState,
        label = "ACCOUNT_DETAILS"
    ) {
        when (it) {
            is AmState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AmPadding.Details.value),
                ) {
                    val account = it.data.account
                    val balanceDetails = it.data.balanceDetails
                    AccountCard(
                        modifier = Modifier,
                        title = account.name,
                        imageUrl = account.imageUrl, loading = account.pending,
                        withDetails = true,
                        creditorDebtor = CardBalanceDetails.CreditorDebtor(
                            debtor = balanceDetails.formattedDebtor,
                            creditor = balanceDetails.formattedCreditor,
                            netDebtorAbs = balanceDetails.formattedNetDebtor,
                            isPositiveDebtor = balanceDetails.isPositiveDebtor,
                        ),
                        incomeExpenses = CardBalanceDetails.IncomeExpenses(
                            income = balanceDetails.formattedIncome,
                            expenses = balanceDetails.formattedExpenses,
                            netIncomeAbs = balanceDetails.formattedNetIncome,
                            isPositiveIncome = balanceDetails.isPositiveIncome,
                        ),
                        currencySymbol = balanceDetails.currency.currencySymbol,
                    )
                    AmLazyColumn(
                        isRefreshing = false,
                        onRefresh = accountDetailsState.refreshTransactions,
                        content = {
                            items(
                                count = transactionsLazyPaging.itemCount,
                                contentType = { LAZY_ITEM_TRANSACTION },
                                key = transactionsLazyPaging.itemKey { transaction -> transaction.transactionID },
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
//                                                accountDetailsState.navigateToTransactionDetails(
//                                                    accountID = transaction.accountID,
//                                                    transactionID = transaction.transactionID
//                                                )
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            }
            is AmState.Error -> Unit
            is AmState.Loading -> Unit
        }
    }


}