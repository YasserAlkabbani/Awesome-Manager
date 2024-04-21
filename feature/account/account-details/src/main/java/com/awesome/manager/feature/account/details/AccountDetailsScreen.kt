package com.awesome.manager.feature.account.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.extentions.limitName
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.TransactionCard

@Composable
fun AccountDetailsRoute(
    sendMainAction: (MainActions) -> Unit,
    accountDetailsViewModel: AccountDetailsViewModel = hiltViewModel()
) {
    val accountDetailsState: AccountDetailsState = accountDetailsViewModel.accountDetailsState

    val navigationAction = accountDetailsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendAction(sendMainAction, accountDetailsState::resetNavigationAction)
    })

    val appBarAction = accountDetailsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendAction(sendMainAction, accountDetailsState::resetAppBar)
    })

    val bottomSheetAction = accountDetailsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendAction(sendMainAction)
    })

    val accountState = accountDetailsState.amAccount.collectAsState().value
    val allowToUpdate = accountDetailsState.allowToUpdate.collectAsState().value
    if (accountState is DataState.Success && allowToUpdate is DataState.Success) {
        accountDetailsState.showReadAppBar(
            title = stringResource(R.string.edit_account_name, accountState.data.name.limitName()),
            onBack = accountDetailsState::navigatePopBack,
            onEdit = { accountDetailsState.navigateToEditAccount(accountState.data.id) },
            canEdit = allowToUpdate.data,
            onAddTransaction = { accountDetailsState.navigateToCreateTransaction(accountState.data.id) }
        )
    }

    AccountDetailsScreen(accountDetailsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailsScreen(accountDetailsState: AccountDetailsState) {

    val accountState: DataState<AmAccount> =
        accountDetailsState.amAccount.collectAsState().value
    val transactionsState: DataState<List<AmTransaction>> =
        accountDetailsState.amTransactions.collectAsState().value

    Column(Modifier.fillMaxSize()) {

        when (accountState) {
            is DataState.Success -> {
                val account = accountState.data
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    creditor = account.creditor,
                    debtor = account.debtor,
                    currency = account.currency.currencyCode,
                    loading = account.pending,
                    onClick = { },
                    onAddTransaction = { accountDetailsState.navigateToCreateTransaction(account.id) },
                    onEditTransaction = null
                )

            }

            DataState.Error, DataState.Loading -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (transactionsState) {
            is DataState.Success -> {
                val transactions = transactionsState.data
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    content = {
                        items(
                            items = transactions,
                            contentType = { "TRANSACTIONS" },
                            key = { transactions -> transactions.id },
                            itemContent = { transaction ->
                                TransactionCard(
                                    modifier = Modifier.animateItemPlacement(),
                                    account = "ACCOUNT",
                                    title = transaction.title,
                                    subTitle = transaction.subtitle,
                                    amount = transaction.amount,
                                    pending = transaction.pending,
                                    date = transaction.updatedAt,
                                    transactionType = transaction.transactionType.title,
                                    isPay = transaction.paymentTransaction,
                                    currency = transaction.currency.currencySymbol,
                                    createdBy = transaction.creatorUserId,
                                    onClick = {
                                        accountDetailsState.navigateToTransaction(
                                            transaction.id
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            }

            DataState.Error, DataState.Loading -> {}
        }

    }


}