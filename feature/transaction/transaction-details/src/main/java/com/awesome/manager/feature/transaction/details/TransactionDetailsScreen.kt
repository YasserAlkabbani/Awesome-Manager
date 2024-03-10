package com.awesome.manager.feature.transaction.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.AppBarAction
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.component.AmTextWithLabel
import com.awesome.manager.core.ui.AccountCard

@Composable
fun TransactionDetailsRoute(
    sendMainAction: (MainActions) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel()
) {

    val transactionDetailsState = transactionDetailsViewModel.transactionDetailsState

    val navigationState = transactionDetailsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationState, block = {
        navigationState.sendAction(
            sendMainAction = sendMainAction,
            resetNavigation = transactionDetailsState::resetNavigationAction
        )
    })

    val transactionState = transactionDetailsState.transaction.collectAsState().value
    LaunchedEffect(key1 = transactionState, block = {
        when (transactionState) {
            is DataState.Success -> {
                val transaction=transactionState.data
                AppBarAction.Read(
                    onBack = transactionDetailsState::navigatePopBack,
                    title = transaction.title,
                    onEdit = {
                        transactionDetailsState.navigateToEditTransaction(transaction.id)
                    }
                ).sendAction(sendMainAction)
            }
            DataState.Error, DataState.Loading -> {}
        }
    })

    TransactionDetailsScreen(transactionDetailsState)
}

@Composable
fun TransactionDetailsScreen(
    transactionDetailsState: TransactionDetailsState
) {

    val transactionState = transactionDetailsState.transaction.collectAsState().value
    val accountState = transactionDetailsState.account.collectAsState().value

    Column(
        modifier = Modifier.padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        when (accountState) {
            is DataState.Success -> {
                val account=accountState.data
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    creditor = account.creditor,
                    debtor = account.debtor,
                    currency = account.currency.currencyCode,
                    loading = account.pending,
                    onClick = { transactionDetailsState.navigateToAccount(account.id) },
                    onAddTransaction = {
                        transactionDetailsState.navigateToCreateTransaction(
                            account.id
                        )
                    },
                    onEditTransaction = null
                )

            }
            DataState.Error , DataState.Loading -> {}
        }
        when (transactionState) {
            is DataState.Success -> {
                val transaction=transactionState.data
                AmTextWithLabel(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.transaction_subject),
                    text = transaction.title,
                    positive = transaction.paymentTransaction,
                )
                AmTextWithLabel(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.transaction_description),
                    text = transaction.subtitle,
                    positive = transaction.paymentTransaction
                )
                AmTextWithLabel(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.amount),
                    text = (transaction.amount).toString(),
                    positive = transaction.paymentTransaction
                )

                AmTextWithLabel(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.payment_type),
                    text = transaction.transactionType.title,
                    positive = transaction.paymentTransaction
                )
            }
            DataState.Error , DataState.Loading -> {}
        }

    }


}