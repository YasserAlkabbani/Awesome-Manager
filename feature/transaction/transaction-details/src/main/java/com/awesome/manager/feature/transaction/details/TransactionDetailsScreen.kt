package com.awesome.manager.feature.transaction.details

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmTextWithLabel
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.ui.card.AccountCard

@Composable
fun TransactionDetailsRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel()
) {

    val context: Context = LocalContext.current
    val transactionDetailsState: TransactionDetailsActions =
        transactionDetailsViewModel.transactionDetailsState

    val mainAction = transactionDetailsState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, transactionDetailsState::doneMainAction)
    }

    val transactionState =
        transactionDetailsState.transactionDetailsData.collectAsState().value
    LaunchedEffect(key1 = transactionState) {
        when (transactionState) {
            is AmUIState.Success -> {
//                val transactionData = transactionState.data
//                when (transactionData.allowToUpdate) {
//                    true -> {
//                        transactionDetailsState.dynamicFabButton(
//                            dynamicFabButton = DynamicFabButton(
//                                t
//                            ),
//                            onClick = {
//                                transactionDetailsState.navigateToEditTransaction(
//                                    accountId = transactionData.transaction.accountId,
//                                    transactionId = transactionData.transaction.id
//                                )
//                            },
//                            dynamicFabExtraButton = DynamicFabExtraButton.Back(),
//                        )
//                    }
//
//                    false -> {
//                        transactionDetailsState.dynamicFabMessage(
//                            text = "Transaction Details",
//                            positive = false,
//                            dynamicFabExtraButton = DynamicFabExtraButton.Back,
//                        )
//                    }
//                }
            }

            is AmUIState.Error, is AmUIState.Loading -> {}
        }
    }

    TransactionDetailsScreen(transactionDetailsState)
}

@Composable
fun TransactionDetailsScreen(
    transactionDetailsState: TransactionDetailsActions
) {

    val transactionDetailsData =
        transactionDetailsState.transactionDetailsData.collectAsState().value

    when (transactionDetailsData) {
        is AmUIState.Success -> {
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
                    income = balanceDetails.formattedIncome,
                    expenses = balanceDetails.formattedExpenses,
                    netIncomeAbs = balanceDetails.formattedNetIncome,
                    debtor = balanceDetails.formattedDebtor,
                    creditor = balanceDetails.formattedCreditor,
                    netDebtorAbs = balanceDetails.formattedNetDebtor,
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

        is AmUIState.Error, is AmUIState.Loading -> {}
    }

}