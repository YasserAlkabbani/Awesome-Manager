package com.awesome.manager.feature.transaction.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.text.AmTextWithLabel
import com.awesome.manager.core.designsystem.text.asString
import com.awesome.manager.core.ui.card.AccountCardWithDetails
import com.awesome.manager.core.ui.card.BalanceData

@Composable
fun TransactionDetailsScreen(
    transactionDetailsViewModel: TransactionDetailsViewModel = hiltViewModel(),
) {
    val transactionDetailsState: TransactionDetailsState =
        transactionDetailsViewModel.transactionDetailsState

//    transactionDetailsState.transactionDetailsUI.collectAsStateWithLifecycle(null)

//    val mainAction = transactionDetailsState.mainAction.collectAsStateWithLifecycle().value
//    LaunchedEffect(key1 = mainAction) {
//        mainAction?.sendMainAction(sendMainAction, transactionDetailsState::doneMainAction)
//    }



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
                is AmState.Error -> Unit
                is AmState.Loading -> Unit
                is AmState.Success -> {
                    val accountWithDetails = it.data
                    val account = accountWithDetails.account
                    AccountCardWithDetails(
                        modifier = Modifier.fillMaxWidth(),
                        title = account.name,
                        imageUrl = account.imageUrl,
                        loading = account.pending,
                        balanceData = BalanceData.generate(
                            income = accountWithDetails.formattedIncome,
                            expenses = accountWithDetails.formattedExpenses,
                            netIncomeAbs = accountWithDetails.formattedNetIncome,
                            isPositiveIncome = accountWithDetails.isPositiveIncome,
                            debtor = accountWithDetails.formattedDebtor,
                            creditor = accountWithDetails.formattedCreditor,
                            netDebtorAbs = accountWithDetails.formattedNetDebtor,
                            isPositiveDebtor = accountWithDetails.isPositiveDebtor,
                            currencySymbol = accountWithDetails.currencySymbol,
                        )
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
                is AmState.Success -> {
                    val transactionWithDetails = it.data
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Title",
                            text = transactionWithDetails.transaction.title.ifBlank { stringResource(R.string.no_title) },
                            positive = transactionWithDetails.isPositive
                        )
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Description",
                            text = transactionWithDetails.transaction.subtitle.ifBlank { stringResource(R.string.no_description) },
                            positive = transactionWithDetails.isPositive
                        )
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.amount),
                            text = (transactionWithDetails.transaction.amount).toString(),
                            positive = transactionWithDetails.isPositive
                        )
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.date),
                            text = transactionWithDetails.transaction.transactionAtDate,
                            positive = transactionWithDetails.isPositive
                        )
                        AmTextWithLabel(
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.payment_type),
                            text = transactionWithDetails.transactionType.asString(),
                            positive = transactionWithDetails.isPositive
                        )
                    }
                }
                is AmState.Error -> Unit
                is AmState.Loading -> Unit
            }
        }
    }


}