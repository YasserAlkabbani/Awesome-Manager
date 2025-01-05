package com.awesome.manager.feature.transaction.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.cards.AmCard
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmTextWithLabel
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.card.BalanceDetails

@Composable
fun TransactionDetailsRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel(),
) {
    val transactionDetailsState: TransactionDetailsState =
        transactionDetailsViewModel.transactionDetailsState

    transactionDetailsState.transactionDetailsUI.collectAsStateWithLifecycle(null)

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

    val transactionState =
        transactionDetailsState.transaction.collectAsState().value
    val accountState =
        transactionDetailsState.account.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AmPadding.Details.value),
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = accountState,
            label = "ACCOUNT_DETAILS",
            contentAlignment = Alignment.TopCenter
        ) {
            when (it) {
                is AmUIState.Error -> Unit
                is AmUIState.Loading -> Unit
                is AmUIState.Success -> {
                    val account = it.data
                    val balanceDetails = account.balanceDetails
                    AccountCard(
                        modifier = Modifier.fillMaxWidth(),
                        title = account.name,
                        imageUrl = account.imageUrl,
                        loading = account.pending, withDetails = true,
                        onClick = { transactionDetailsState.navigateToAccountDetails(account.id) },
                        incomeExpenses = BalanceDetails.IncomeExpenses(
                            income = balanceDetails.formattedIncome,
                            expenses = balanceDetails.formattedExpenses,
                            netIncomeAbs = balanceDetails.formattedNetIncome,
                            isPositiveIncome = balanceDetails.isPositiveIncome,
                        ),
                        creditorDebtor = BalanceDetails.CreditorDebtor(
                            debtor = balanceDetails.formattedDebtor,
                            creditor = balanceDetails.formattedCreditor,
                            netDebtorAbs = balanceDetails.formattedNetDebtor,
                            isPositiveDebtor = balanceDetails.isPositiveDebtor
                        ),
                        currencySymbol = balanceDetails.currency.currencySymbol,
                    )
                }
            }
        }

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = transactionState,
            label = "TRANSACTION_DETAILS",
            contentAlignment = Alignment.TopCenter
        ) {
            when (it) {
                is AmUIState.Success -> {
                    val transaction = it.data
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Title",
                            text = transaction.title.ifBlank { stringResource(R.string.no_title) },
                            positive = transaction.transactionType.positive
                        )
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Description",
                            text = transaction.subtitle.ifBlank { stringResource(R.string.no_description) },
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
                is AmUIState.Error -> Unit
                is AmUIState.Loading -> Unit
            }
        }
    }


}