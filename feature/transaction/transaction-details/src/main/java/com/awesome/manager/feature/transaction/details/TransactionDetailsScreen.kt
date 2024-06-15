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
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.actions.appbar.sendMainAction
import com.awesome.manager.core.designsystem.actions.bottomsheet.sendMainAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.sendMainAction
import com.awesome.manager.core.designsystem.component.text.AmTextWithLabel
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.ui.card.AccountCard

@Composable
fun TransactionDetailsRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel()
) {

    val transactionDetailsState = transactionDetailsViewModel.transactionDetailsState

    val navigationAction = transactionDetailsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(
            sendMainAction, transactionDetailsState::doneNavigationAction
        )
    })

    val appBarAction = transactionDetailsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, transactionDetailsState::doneAppBarAction)
    })

    val bottomSheetAction = transactionDetailsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(
            sendMainAction, transactionDetailsState::doneBottomSheetAction
        )
    })

    val transactionState =
        transactionDetailsState.transactionDetailsData.collectAsState().value
    val editTransactionText = stringResource(R.string.edit_transaction)
    LaunchedEffect(key1 = transactionState) {
        when (transactionState) {
            is DataState.Success -> {
                val transactionData = transactionState.data
                transactionDetailsState.setForTransactionDetailsScreen(
                    editButtonText = editTransactionText,
                    allowToEdit = transactionData.allowToUpdate,
                    onClickBack = transactionDetailsState::navigatePopBack,
                    onEditButton = {
                        transactionDetailsState.navigateToEditTransaction(
                            accountId = transactionData.transaction.accountId,
                            transactionId = transactionData.transaction.id
                        )
                    },
                )
            }

            DataState.Error, DataState.Loading -> {}
        }
    }

    TransactionDetailsScreen(transactionDetailsState)
}

@Composable
fun TransactionDetailsScreen(
    transactionDetailsState: TransactionDetailsStateMain
) {

    val transactionDetailsData =
        transactionDetailsState.transactionDetailsData.collectAsState().value

    when (transactionDetailsData) {
        is DataState.Success -> {
            val transaction = transactionDetailsData.data.transaction
            val account = transactionDetailsData.data.account
            Column(
                modifier = Modifier.padding(horizontal = AmPadding.MEDIUM.value),
                verticalArrangement = Arrangement.spacedBy(AmPadding.SMALL.value),
            ) {
                val balanceDetails = account.balanceDetails
                AccountCard(
                    modifier = Modifier,
                    title = account.name,
                    imageUrl = account.imageUrl,
                    loading = account.pending, withDetails = true,
                    onClick = { transactionDetailsState.navigateToAccountDetails(account.id) },
                    onAddTransaction = null,
                    onEditTransaction = null,
                    income = balanceDetails.income, expenses = balanceDetails.expenses,
                    netIncomeAbs = balanceDetails.netIncomeAbs,
                    debtor = balanceDetails.debtor, creditor = balanceDetails.creditor,
                    netDebtorAbs = balanceDetails.netDebtorAbs,
                    currencySymbol = balanceDetails.currency.currencySymbol,
                    isPositiveIncome = balanceDetails.isPositiveIncome,
                    isPositiveDebtor = balanceDetails.isPositiveDebtor
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(AmPadding.X_SMALL.value),
                ) {
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.transaction_subject),
                        text = transaction.title,
                        positive = transaction.transactionType.positive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.transaction_description),
                        text = transaction.subtitle,
                        positive = transaction.transactionType.positive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.amount),
                        text = (transaction.amount).toString(),
                        positive = transaction.transactionType.positive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.date),
                        text = transaction.transactionAtDate,
                        positive = transaction.transactionType.positive
                    )
                    AmTextWithLabel(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.payment_type),
                        text = transaction.transactionType.getString(),
                        positive = transaction.transactionType.positive
                    )

                }
            }
        }

        DataState.Error, DataState.Loading -> {}
    }

}