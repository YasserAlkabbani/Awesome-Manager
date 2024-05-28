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
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.ui_actions.navigation.sendMainAction
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
        navigationAction.sendMainAction(sendMainAction, accountDetailsState::resetNavigationAction)
    })

    val appBarAction = accountDetailsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, accountDetailsState::resetAppBar)
    })

    val bottomSheetAction = accountDetailsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, accountDetailsState::idleBottomSheet)
    })

    val accountState = accountDetailsState.amAccount.collectAsState().value
    val allowToUpdate = accountDetailsState.allowToUpdate.collectAsState().value

    val editAccount = stringResource(R.string.edit_account_name)
    LaunchedEffect(key1 = accountState, allowToUpdate) {
        if (accountState is DataState.Success && allowToUpdate is DataState.Success) {
            accountDetailsState.showReadAppBar(
                title = "$editAccount ${accountState.data.name}",
                onBack = accountDetailsState::navigatePopBack,
                onEdit = { accountDetailsState.navigateToEditAccount(accountState.data.id) },
                canEdit = allowToUpdate.data,
                onAddTransaction = { accountDetailsState.navigateToCreateTransaction(accountState.data.id) }
            )
        }
    }

    AccountDetailsScreen(accountDetailsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailsScreen(accountDetailsState: AccountDetailsState) {

    val accountState: DataState<AmAccount> = accountDetailsState.amAccount.collectAsState().value
    val transactionsLazyPaging: LazyPagingItems<AmTransaction> =
        accountDetailsState.amTransactions.collectAsLazyPagingItems()

    Column(Modifier.fillMaxSize()) {

        when (accountState) {
            is DataState.Success -> {
                val account = accountState.data
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    creditor = account.balanceDetails.creditor,
                    debtor = account.balanceDetails.debtor,
                    currency = account.balanceDetails.currency.currencyCode,
                    loading = account.pending,
                    onClick = { },
                    onAddTransaction = { accountDetailsState.navigateToCreateTransaction(account.id) },
                    onEditTransaction = null
                )

            }

            DataState.Error, DataState.Loading -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        AmLazyColumn(
            content = {
                items(
                    count = transactionsLazyPaging.itemCount,
                    contentType = { LAZY_ITEM_TRANSACTION },
                    key = transactionsLazyPaging.itemKey { transaction -> transaction.id },
                    itemContent = { index ->
                        transactionsLazyPaging[index]?.let { transaction ->
                            TransactionCard(
                                modifier = Modifier.animateItemPlacement(),
                                account = "ACCOUNT",
                                title = transaction.title,
                                subTitle = transaction.subtitle,
                                amount = transaction.amount,
                                pending = transaction.pending,
                                date = transaction.updatedAt,
                                transactionType = transaction.transactionType.name,
                                isPay = transaction.transactionType.posative,
                                currency = transaction.currency.currencySymbol,
                                createdBy = transaction.creatorUserId,
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