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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.extentions.limitName
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.appbar.sendMainAction
import com.awesome.manager.core.designsystem.actions.bottomsheet.sendMainAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.sendMainAction
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
    val accountDetailsState: AccountDetailsState = accountDetailsViewModel.accountDetailsState

    val navigationAction = accountDetailsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, accountDetailsState::doneNavigationAction)
    })

    val appBarAction = accountDetailsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, accountDetailsState::doneAppBarAction)
    })

    val bottomSheetAction = accountDetailsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, accountDetailsState::doneBottomSheetAction)
    })

    val accountState = accountDetailsState.amAccount.collectAsState().value
    val allowToUpdate = accountDetailsState.allowToUpdate.collectAsState().value

    val editAccount = stringResource(R.string.edit_account_name)
    LaunchedEffect(key1 = accountState, allowToUpdate) {
        if (accountState is DataState.Success && allowToUpdate is DataState.Success) {
            accountDetailsState.setForAccountDetailsScreen(
                onClickBack = accountDetailsState::navigatePopBack,
                editButtonText = "$editAccount ${accountState.data.name.limitName()}",
                onEditButton = { accountDetailsState.navigateToEditAccount(accountState.data.id) },
                allowToEdit = allowToUpdate.data,
                onAddTransaction = { accountDetailsState.navigateToCreateTransaction(accountState.data.id) },
            )
        }
    }

    AccountDetailsScreen(accountDetailsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailsScreen(accountDetailsState: AccountDetailsState) {

    val isLoading: Boolean = accountDetailsState.loading.collectAsState().value
    val accountState: DataState<AmAccount> = accountDetailsState.amAccount.collectAsState().value
    val transactionsLazyPaging: LazyPagingItems<AmTransaction> =
        accountDetailsState.amTransactions.collectAsLazyPagingItems()

    Column(Modifier.fillMaxSize()) {

        when (accountState) {
            is DataState.Success -> {
                val account = accountState.data
                val balanceDetails = account.balanceDetails
                AccountCard(
                    modifier = Modifier, title = account.name,
                    imageUrl = account.imageUrl, loading = account.pending,
                    withDetails = true, onClick = null,
                    onAddTransaction = null, onEditTransaction = null,
                    income = balanceDetails.income, expenses = balanceDetails.expenses,
                    netIncomeAbs = balanceDetails.netIncomeAbs,
                    debtor = balanceDetails.debtor, creditor = balanceDetails.creditor,
                    netDebtorAbs = balanceDetails.netDebtorAbs,
                    currencySymbol = balanceDetails.currency.currencySymbol,
                    isPositiveIncome = balanceDetails.isPositiveIncome,
                    isPositiveDebtor = balanceDetails.isPositiveDebtor,
                )

            }

            DataState.Error, DataState.Loading -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        AmLazyColumn(
            isRefreshing = isLoading,
            onRefresh = accountDetailsState.refreshTransactions,
            content = {
                items(
                    count = transactionsLazyPaging.itemCount,
                    contentType = { LAZY_ITEM_TRANSACTION },
                    key = transactionsLazyPaging.itemKey { transaction -> transaction.id },
                    itemContent = { index ->
                        transactionsLazyPaging[index]?.let { transaction ->
                            TransactionCard(
                                modifier = Modifier.animateItemPlacement(),
                                account = transaction.accountName,
                                title = transaction.title,
                                amount = transaction.amount,
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