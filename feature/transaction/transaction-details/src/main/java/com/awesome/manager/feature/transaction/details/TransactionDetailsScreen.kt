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
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.component.AmTextWithLabel
import com.awesome.manager.core.ui.AccountCard

@Composable
fun TransactionDetailsRoute(
    sendMainAction: (MainActions) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel()
) {

    val transactionDetailsState = transactionDetailsViewModel.transactionDetailsState

    val navigationAction = transactionDetailsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, transactionDetailsState::resetNavigationAction)
    })

    val appBarAction = transactionDetailsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, transactionDetailsState::resetAppBar)
    })

    val bottomSheetAction = transactionDetailsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction,transactionDetailsState::idleBottomSheet)
    })


    when (val transactionState = transactionDetailsState.transactionDetailsData.collectAsState().value) {
        is DataState.Success -> {
            val transactionData=transactionState.data
            transactionDetailsState.showReadAppBar(
                title = "Edit Transaction",
                canEdit = transactionData.allowToUpdate,
                onBack = transactionDetailsState::navigatePopBack,
                onEdit = {transactionDetailsState.navigateToEditTransaction(transactionData.transaction.id)},
                onAddTransaction = null,
            )
        }

        DataState.Error, DataState.Loading -> {}
    }

    TransactionDetailsScreen(transactionDetailsState)
}

@Composable
fun TransactionDetailsScreen(
    transactionDetailsState: TransactionDetailsState
) {

    val transactionDetailsData = transactionDetailsState.transactionDetailsData.collectAsState().value

    when (transactionDetailsData){
        is DataState.Success -> {
            val transaction=transactionDetailsData.data.transaction
            val account=transactionDetailsData.data.account
            Column(
                modifier = Modifier.padding(horizontal = 6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    creditor = account.creditor,
                    debtor = account.debtor,
                    currency = account.currency.currencyCode,
                    loading = account.pending,
                    onClick = { transactionDetailsState.navigateToAccountDetails(account.id) },
                    onAddTransaction = {
                        transactionDetailsState.navigateToCreateTransaction(
                            account.id
                        )
                    },
                    onEditTransaction = null
                )
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
        }
        DataState.Error ,DataState.Loading -> {}
    }

}