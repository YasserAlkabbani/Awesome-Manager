package com.awesome.manager.feature.transaction.details

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmTextWithLabel
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.ui.card.AccountCard

@Composable
fun TransactionDetailsRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel(),
) {
    val transactionDetailsState: TransactionDetailsState =
        transactionDetailsViewModel.transactionDetailsState

    transactionDetailsState.transactionDetailsData.collectAsStateWithLifecycle().value

    val mainAction = transactionDetailsState.mainAction.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, transactionDetailsState::doneMainAction)
    }



    TransactionDetailsScreen(transactionDetailsState)
}

@Composable
fun TransactionDetailsScreen(
    transactionDetailsState: TransactionDetailsState,
) {

    val transactionDetailsData =
        transactionDetailsState.transactionDetailsData.collectAsState().value

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = transactionDetailsData,
        label = "TRANSACTION_DETAILS",
        contentAlignment = Alignment.TopCenter
    ) {
        when (it) {
            is AmUIState.Success -> {
                val transaction = it.data.transaction
                val account = it.data.account
                val balanceDetails = account.balanceDetails
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AmPadding.MEDIUM.value),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AmPadding.SMALL.value),
                ) {
                    AccountCard(
                        modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(AmPadding.X_SMALL.value),
                        horizontalAlignment = Alignment.CenterHorizontally
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

            is AmUIState.Error -> Unit
            is AmUIState.Loading -> Unit
        }
    }


}