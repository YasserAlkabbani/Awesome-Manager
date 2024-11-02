package com.awesome.manager.feature.account.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.AmUIState
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
    accountDetailsViewModel: AccountDetailsViewModel = hiltViewModel()
) {

    val localContext = LocalContext.current
    val accountDetailsState = accountDetailsViewModel.accountDetailsState

    val mainAction = accountDetailsState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, accountDetailsState::doneMainAction)
    }

    val accountState = accountDetailsState.amAccount.collectAsState().value
    val allowToUpdate = accountDetailsState.allowToUpdate.collectAsState().value

    LaunchedEffect(key1 = accountState, allowToUpdate) {
        if (accountState is AmUIState.Success && allowToUpdate is AmUIState.Success) {
            val allowToEdit = allowToUpdate.data
            val account = accountState.data
//            when (allowToEdit) {
//                true -> accountDetailsState.dynamicFabMessage(
//                    positive = false,
//                    text = "${localContext.getString(R.string.edit_account_name)} ${accountState.data.name}",
//                    dynamicFabExtraButton = DynamicFabExtraButton.None
//                )
//
//                else -> accountDetailsState.dynamicFabButton(
//                    text = "${localContext.getString(R.string.edit_account_name)} ${accountState.data.name}",
//                    positive = true,
//                    onClick = { accountDetailsState.navigateToEditAccount(accountState.data.id) },
//                    dynamicFabExtraButton = DynamicFabExtraButton.Edit(
//                        onClick = {
//                            NavigationDestination.TransactionEditor(
//                                accountId = account.id, transactionId = null
//                            )
//                        }
//                    )
//                )
//            }
        }
    }

    AccountDetailsScreen(accountDetailsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailsScreen(accountDetailsState: AccountDetailsState) {

    val accountState: AmUIState<AmAccount> = accountDetailsState.amAccount.collectAsState().value
    val transactionsLazyPaging: LazyPagingItems<AmTransaction> =
        accountDetailsState.amTransactions.collectAsLazyPagingItems()

    Column(Modifier.fillMaxSize()) {

        when (accountState) {
            is AmUIState.Success -> {
                val account = accountState.data
                val balanceDetails = account.balanceDetails
                AccountCard(
                    modifier = Modifier, title = account.name,
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

            }

            is AmUIState.Error, is AmUIState.Loading -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

//        AmLazyColumn(
//            isRefreshing = true,
//            onRefresh = accountDetailsState.refreshTransactions,
//            content = {
//                items(
//                    count = transactionsLazyPaging.itemCount,
//                    contentType = { LAZY_ITEM_TRANSACTION },
//                    key = transactionsLazyPaging.itemKey { transaction -> transaction.id },
//                    itemContent = { index ->
//                        transactionsLazyPaging[index]?.let { transaction ->
//                            TransactionCard(
//                                modifier = Modifier.animateItemPlacement(),
//                                account = transaction.accountName,
//                                title = transaction.title,
//                                amount = transaction.formattedAmount,
//                                pending = transaction.pending,
//                                date = transaction.transactionAtDate,
//                                transactionType = transaction.transactionType.getString(),
//                                isPay = transaction.transactionType.positive,
//                                currency = transaction.currency.currencySymbol,
//                                onClick = {
//                                    accountDetailsState.navigateToTransactionDetails(
//                                        transaction.id
//                                    )
//                                }
//                            )
//                        }
//                    }
//                )
//            }
//        )

    }


}